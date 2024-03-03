package com.kunkun.oaBlack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class OaBlackApplication {

    public static void main(String[] args) {
        SpringApplication.run(OaBlackApplication.class, args);
    }

}
