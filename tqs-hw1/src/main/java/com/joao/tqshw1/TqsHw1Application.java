package com.joao.tqshw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

@SpringBootApplication
public class TqsHw1Application {

    @Autowired
    private StationService service;

    public static void main(String[] args) {
        SpringApplication.run(TqsHw1Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) { return builder.build(); }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        /**
         * A API utilizada não possui endpoint com todas as cidades
        por isso optei por escolher algumas das possíveis
         */
        int ctr = 0;
        ArrayList<String> city_names = new ArrayList<>();
        city_names.add("Shanghai");city_names.add("Paris");
        city_names.add("London");city_names.add("Lisbon");
        city_names.add("Berlin");city_names.add("Tokyo");
        city_names.add("Munchen");city_names.add("Denver");
        city_names.add("Helsinki");city_names.add("Stockholm");
        city_names.add("Moscow");city_names.add("Madrid");

        ArrayList<Station> aux = new ArrayList<>();

        while (ctr<city_names.size()){
            Station st = new Station(ctr,city_names.get(ctr));
            aux.add(st);
            ctr+=1;
        }
        return args -> {
            for(int i=0;i<aux.size();i++){
                service.saveStation(aux.get(i).getID(),aux.get(i));
            }
        };
    }

}
