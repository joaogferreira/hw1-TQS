package com.joao.tqshw1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private AirQualityService service_air = new AirQualityService();

    @Autowired
    private StationService service_station = new StationService();

    @Autowired
    private RestTemplate restTemplate;
    private String token = "366f681a7e36acc422397bb0c8f572d2e106ee04";

    @GetMapping("/air/{city}")
    public AirQuality air(@PathVariable String city){
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

    private void refresh_city(String city){
        AirQuality air_quality = restTemplate.getForObject("https://api.waqi.info/feed/"+city+"/?token="+token,AirQuality.class);
        service_air.saveAirQuality(city,air_quality);
    }


    @GetMapping("/stations")
    public Map<Integer,Station> stations(){
        return service_station.returnStation();
    }


    @GetMapping("/stats")
    public String getStats() {return "Hits: " + Cache.getHit() + "/nMiss: " + Cache.getMiss();}

    @GetMapping("/station/{city}")
    public void SpecificStation(@PathVariable String city){
        Map<Integer,Station> aux = service_station.returnStation();

        for(int i=0;i<aux.size();i++){
            if(aux.get(i).getCity()==city){

            }else{
                //erro
            }
        }

        if(!service_station.returnStation().containsKey(city)){
            //this.refresh(city);
            //service_air.incrementMiss();
        }

    }


}
