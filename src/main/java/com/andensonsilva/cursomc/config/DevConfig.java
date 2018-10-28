package com.andensonsilva.cursomc.config;

import com.andensonsilva.cursomc.services.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.text.ParseException;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    DBService dbService;

    /**
     * Obtém o valor da chave do aplication.properties
     */
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String strategy;

    @Bean
    public boolean instantiateDataBase() throws ParseException {

        if (!"create".equals(strategy)) {
            return false;
        }

        dbService.instatiateDatabase();
        return true;
    }
}
