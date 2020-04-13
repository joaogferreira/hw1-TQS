package com.joao.tqshw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class AirQualityController {
    /**
     * Esta classe funciona como Controller
     * É aqui que são definidos os endpoints
     */
    @Autowired
    private AirQualityService serviceAir;

    @Autowired
    private StationService serviceStation;

    @Autowired
    private RestTemplate restTemplate;

    //API Key
    private String token = "366f681a7e36acc422397bb0c8f572d2e106ee04";

    /** Dada uma cidade é retornado o objecto AirQuality associado com os valores registados
     * Verifica se o valor já se encontra em Cache. No caso de não se encontrar é feito um pedido à API (refreshCity)
     * Verifica se o valor que se encontra em cache é válido (não foi registado há mais de mais minutos)
     * Se o valor já tiver sido registado há mais de 10 minutos é feita uma chamada à API (refreshCity)
     * Nos dois casos acima é incrementado o número de tentativas sem sucesso (miss)
     * Para o último caso possível,
     * é incrementado o número de sucessos e retornado o objecto AirQuality de uma determinada cidade
     */
    @GetMapping("/air/{city}")
    public AirQuality getAirQuality(@PathVariable String city){
        if(!serviceAir.returnAirQuality().containsKey(city) || System.currentTimeMillis() - serviceAir.returnAirQuality().get(city).getTime() > 600000){
            this.refreshCity(city);
            serviceAir.incrementMiss();
        }
        /**else if(System.currentTimeMillis() - serviceAir.returnAirQuality().get(city).getTime() > 600000) { //10 minutos = 60*10*1000 ms
            this.refreshCity(city);
            serviceAir.incrementMiss();
        }*/
        else {
            serviceAir.incrementHit();
        }

        return serviceAir.returnAirQuality().get(city);
    }


    /**
     * Método para fazer chamadas à API para uma determinada cidade
     */
    private void refreshCity(String city){ //Guardar na cache pelo que não é contabilizado como sendo um request
        AirQuality airQuality = restTemplate.getForObject("https://api.waqi.info/feed/"+city+"/?token="+token,AirQuality.class);
        serviceAir.saveAirQuality(city,airQuality);
    }



    /**
     * Método para retornar todas as stations guardadas em cache
     */
    @GetMapping("/stations")
    public Map<Integer,Station> getStations(){
        return serviceStation.returnStation();
    }



    @GetMapping("/requests")
    public String getRequests(){ return "Requests: " + Cache.countRequests(); } //Retorna TODOS os requests (hits,miss, e acesso a endpoints)
    //O html também acede à API produzida por mim, pelo que também irá incrementar o número de requests



    /**
     * Método para retornar as estatisticas guardadas em cache
     */
    @GetMapping("/stats")
    public String getStats() {
        return "Hits: " + serviceStation.getHit() + "<br>Miss: " + serviceStation.getMiss();
    }



    /**
     * Verifica se existe uma estação numa determinada cidade
     * Se existir retorna a cidade e o ID, incrementando os hits
     * Se não existir retorna "Station not found" , incrementando o valor de Miss
     */
    @GetMapping("/station/{city}")
    public String getSpecificStation(@PathVariable String city){
        Map<Integer,Station> aux = serviceStation.returnStation();

        //As stations não têm TTL pelo que apenas poderá ser avaliado se o valor esperado está em cache ou não
        for(int i=0;i<aux.size();i++){
            if (aux.get(i)!=null && aux.get(i).getCity().equalsIgnoreCase(city.toLowerCase()) ){
                serviceStation.incrementHit();
                return "There is a station in " + city.substring(0, 1).toUpperCase() + city.substring(1)
                        + "!<br>City: " + aux.get(i).getCity() + "<br>ID: " + Integer.toString(aux.get(i).getID());
            }
        }
        serviceStation.incrementMiss();
        return "Station not found.";
    }


}
