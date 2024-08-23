package com.skydev.ejemplo2_springdatajpa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.skydev.ejemplo2_springdatajpa.repository")
public class JPAConfiguratin {

}
