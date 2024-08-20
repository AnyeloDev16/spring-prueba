package com.skydev.ejemplo2_springdatajpa.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class EmailConfiguration {

    @Value("${email.sender}")
    private String emailUser;

    @Value("${email.password}")
    private String emailPass;

    @Bean
    public JavaMailSender getJavaMailSender(){

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(emailUser);
        mailSender.setPassword(emailPass);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); //Indicamos el protocolo a usar (smtp)
        props.put("mail.smtp.auth", "true"); //Habilitamos la auteticación de usuario y contraseña al ingresar
        props.put("mail.smtp.starttls.enable", "true"); //Habilitabdo el cifrado de la comunicación(correo electronico cifrado, mensajes, etc)
        props.put("mail.debug", "true"); //Muestra info en la consola (desarrollo)

        return mailSender;

    }

}
