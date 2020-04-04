package com.joao.tqshw1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class TqsHw1Application {

    public static void main(String[] args) {
        SpringApplication.run(TqsHw1Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) { return builder.build(); }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        //Falta implementar
        return null;
    }

}
