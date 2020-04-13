package com.joao.tqshw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;

@SpringBootApplication
public class TqsHw1Application {

    @Autowired
    private StationService service;

    //Run the App
    public static void main(String[] args) {
        SpringApplication.run(TqsHw1Application.class);
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

        ArrayList<String> cityNames = new ArrayList<>();
        cityNames.add("Shanghai");cityNames.add("Paris");cityNames.add("London");cityNames.add("Lisbon");
        cityNames.add("Berlin");cityNames.add("Tokyo");cityNames.add("Munchen");cityNames.add("Denver");
        cityNames.add("Helsinki");cityNames.add("Stockholm");cityNames.add("Moscow");cityNames.add("Madrid");

        ArrayList<Station> aux = new ArrayList<>();

        //Guardar objectos station num arrayList para depois guarda-las na Cache
        Station st;
        while (ctr<cityNames.size()){
            st = new Station(ctr,cityNames.get(ctr));
            aux.add(st);
            ctr+=1;
        }

        //Guardar as stations disponíveis para cada cidade, com um id associado
        return args -> {
            for(int i=0;i<aux.size();i++){
                service.saveStation(aux.get(i).getID(),aux.get(i));
            }
        };
    }

}
