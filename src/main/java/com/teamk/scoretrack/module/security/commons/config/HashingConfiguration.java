package com.teamk.scoretrack.module.security.commons.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class HashingConfiguration {
    public static final String BCRYPT = "Bcrypt";
    public static final String PBKDF2 = "Pbkdf2";

    @Bean(BCRYPT)
    @Primary
    public PasswordEncoder bcrypt() {
        return new BCryptPasswordEncoder();
    }

    /*@Bean(PBKDF2)
    public PasswordEncoder pbkdf2() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }*/
}
