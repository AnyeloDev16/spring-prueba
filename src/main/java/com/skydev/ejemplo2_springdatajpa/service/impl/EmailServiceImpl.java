package com.skydev.ejemplo2_springdatajpa.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.skydev.ejemplo2_springdatajpa.service.IEmailService;

@Service
public class EmailServiceImpl implements IEmailService{

    @Value("${email.sender}")
    private String emailUserSender;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmailRegisterSuccessful(String emailUser) {
        
        SimpleMailMessage smm = new SimpleMailMessage();

        smm.setFrom(emailUserSender);
        smm.setTo(emailUser);
        smm.setSubject("REGISTRO EXITOSO");
        smm.setText("Le damos la bienvenida a nuestro sistema.");

        javaMailSender.send(smm);

    }
    
    @Override
    public void sendEmailDeleteSuccessful(String emailUser) {
        
        SimpleMailMessage smm = new SimpleMailMessage();

        smm.setFrom(emailUserSender);
        smm.setTo(emailUser);
        smm.setSubject("CUENTA ELIMINADA");
        smm.setText("Su cuenta fue eliminada con exito, esperamos volverlo a verlo en un futuro.");

        javaMailSender.send(smm);

    }

}
