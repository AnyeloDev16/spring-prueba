package com.skydev.ejemplo2_springdatajpa.aspect;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.skydev.ejemplo2_springdatajpa.entity.Audit;
import com.skydev.ejemplo2_springdatajpa.exception.custom.EntityNotFoundException;
import com.skydev.ejemplo2_springdatajpa.service.IAuditService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {

    private final IAuditService auditService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    static{
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Pointcut(value = "execution (* com.skydev.*ejemplo2_springdatajpa.service.impl.*.save*(..)) && !within(com.skydev.*ejemplo2_springdatajpa.service.impl.AuditServiceImpl) && !within(com.skydev.*ejemplo2_springdatajpa.service.impl.EmailServiceImpl)")
    private void methodSave(){}

    @Pointcut(value = "execution (* com.skydev.*ejemplo2_springdatajpa.service.impl.*.update*(..)) && !within(com.skydev.*ejemplo2_springdatajpa.service.impl.AuditServiceImpl) && !within(com.skydev.*ejemplo2_springdatajpa.service.impl.EmailServiceImpl)")
    private void methodUpdate(){}

    @Pointcut(value = "execution (* com.skydev.*ejemplo2_springdatajpa.service.impl.*.delete*(..)) && !within(com.skydev.*ejemplo2_springdatajpa.service.impl.AuditServiceImpl) && !within(com.skydev.*ejemplo2_springdatajpa.service.impl.EmailServiceImpl)")
    private void methodDelete(){}

    @Around(value = "methodSave()")
    public Object aroundAuditSave(ProceedingJoinPoint joinPoint){
        //Antes de la ejecución
        //Ejecución
        Object result = null;
        try{
            result = (Object)joinPoint.proceed();
        } catch (Throwable ex){
            ex.printStackTrace();
        }
        //Despues de la ejecución
        this.audit(result, ActionType.INSERT);
        return result;
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Around(value = "methodUpdate()")
    public Object aroundAuditUpdate(ProceedingJoinPoint joinPoint){
        //Antes de la ejecución
        //Ejecución
        List<Object> result = null;
        try{
            result = (List<Object>)joinPoint.proceed();
        } catch (EntityNotFoundException enfe){
            throw enfe;
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        //Despues de la ejecución
        Object previousData = result.get(1);
        result.remove(1);
        this.audit(previousData, ActionType.UPDATE);
        return result;
    }

    @Around(value = "methodDelete()")
    public Object aroundAuditDelete(ProceedingJoinPoint joinPoint){
        //Antes de la ejecución
        //Ejecución
        Object result = null;
        try{
            result = joinPoint.proceed();
        } catch (EntityNotFoundException enfe){
            throw enfe;
        }catch (Throwable ex){
            ex.printStackTrace();
        }
        //Despues de la ejecución
        Object previousData = result;
        this.audit(previousData, ActionType.DELETE);
        return result;
    }

        // METODOS HELPER

    private void audit(Object previousObject, ActionType actionType){
        
        String nameEntity = previousObject.getClass().getSimpleName();
        Long idObject = this.getId(previousObject);
        String mapPreviousObject = this.convertToJson(previousObject);
        auditService.save(new Audit(nameEntity,idObject,actionType.name(),mapPreviousObject, LocalDateTime.now()));

    }

    private Long getId(Object o){
        try{

            Field[] fields = o.getClass().getDeclaredFields();

            Field field = List.of(fields).stream()
                .filter(f -> f.getName().equalsIgnoreCase("id"))
                .findFirst()
                .get();

            field.setAccessible(true);

            return (Long) field.get(o);

        } catch (IllegalAccessException ex){
            ex.printStackTrace();
            return null;
        }
    }

    private String convertToJson(Object o){
        try{           
            return objectMapper.writeValueAsString(o);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}

enum ActionType{

    INSERT,
    UPDATE,
    DELETE

}

