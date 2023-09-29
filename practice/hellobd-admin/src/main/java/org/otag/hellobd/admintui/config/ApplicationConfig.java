package org.otag.hellobd.admintui.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.otag.hellobd.admintui.Global;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Configuration
public class ApplicationConfig {
    @Bean
    public EntityManagerFactory emf() {
        return Persistence.createEntityManagerFactory("hellobd-admintui");
    }

    @Bean
    public EntityManager em() {
        return emf().createEntityManager();
    }

    @Bean
    public BufferedReader br() {
        return new BufferedReader(new InputStreamReader(System.in));
    }
}
