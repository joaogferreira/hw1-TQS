package com.joao.tqshw1;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class AirQualityController {
    /**
     * Esta classe funciona como Controller
     * É aqui que são definidos os endpoints
     */
    @Autowired
    private AirQualityService service_air;

    @Autowired
    private StationService service_station;

    @Autowired
    private RestTemplate restTemplate;

    //API Key
    private String token = "366f681a7e36acc422397bb0c8f572d2e106ee04";

    /** Dada uma cidade é retornado o objecto AirQuality associado com os valores registados
     * Verifica se o valor já se encontra em Cache. No caso de não se encontrar é feito um pedido à API (refresh_city)
     * Verifica se o valor que se encontra em cache é válido (não foi registado há mais de mais minutos)
     * Se o valor já tiver sido registado há mais de 10 minutos é feita uma chamada à API (refresh_city)
     * Nos dois casos acima é incrementado o número de tentativas sem sucesso (miss)
     * Para o último caso possível,
     * é incrementado o número de sucessos e retornado o objecto AirQuality de uma determinada cidade
     */
    @GetMapping("/air/{city}")
    public AirQuality getAirQuality(@PathVariable String city){
        if(!service_air.returnAirQuality().containsKey(city)){
            this.refresh_city(city);
            service_air.incrementMiss();
        }
        else if(System.currentTimeMillis() - service_air.returnAirQuality().get(city).getTime() > 600000) { //10 minutos = 60*10*1000 ms
            this.refresh_city(city);
            service_air.incrementMiss();
        } else {
            service_air.incrementHit();
        }

        return service_air.returnAirQuality().get(city);
    }

    /**
     * Método para fazer chamadas à API para uma determinada cidade
     */
    private void refresh_city(String city){
        AirQuality air_quality = restTemplate.getForObject("https://api.waqi.info/feed/"+city+"/?token="+token,AirQuality.class);
        service_air.saveAirQuality(city,air_quality);
    }

    /**
     * Método para retornar todas as stations guardadas em cache
     */
    @GetMapping("/stations")
    public Map<Integer,Station> getStations(){
        return service_station.returnStation();
    }

    /**
     * Método para retornar as estatisticas guardadas em cache
     */
    @GetMapping("/stats")
    public String getStats() {
        return "Hits: " + service_station.getHit() + "<br>Miss: " + service_station.getMiss();
        //return "Hits: " + service_air.getHit() + "<br>Miss: " + service_air.getMiss();
    }

    /**
     * Verifica se existe uma estação numa determinada cidade
     * Se existir retorna a cidade e o ID, incrementando os hits
     * Se não existir retorna "Station not found" , incrementando o valor de Miss
     */
    @GetMapping("/station/{city}")
    public String getSpecificStation(@PathVariable String city){
        Map<Integer,Station> aux = service_station.returnStation();

        //As stations não têm TTL pelo que apenas poderá ser avaliado se o valor esperado está em cache ou não
        for(int i=0;i<aux.size();i++){
            if (aux.get(i)!=null) {
                if (aux.get(i).getCity().toLowerCase().equals(city.toLowerCase())) {
                    service_station.incrementHit();
                    return "There is a station in " + city.substring(0, 1).toUpperCase() + city.substring(1)
                            + "!<br>City: " + aux.get(i).getCity() + "<br>ID: " + Integer.toString(aux.get(i).getID());
                }
            }
        }
        service_station.incrementMiss();
        return "Station not found.";
    }


}
