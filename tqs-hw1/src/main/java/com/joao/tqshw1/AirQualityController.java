package com.joao.tqshw1;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private RestTemplate restTemplate;
    private String token = "366f681a7e36acc422397bb0c8f572d2e106ee04";

    //http://localhost:8080/api/air/london
    @GetMapping("/air/{city}")
    public AirQuality air(@PathVariable String city){
        if(!Cache.airQuality.containsKey(city))
            this.refresh(city);
        if(System.currentTimeMillis() - Cache.airQuality.get(city).getTime() > 10000){ //10 segundos
            this.refresh(city);
        }
        return Cache.airQuality.get(city);
    }

    private void refresh(String city){
        AirQuality air_qual = restTemplate.getForObject("https://api.waqi.info/feed/"+city+"/?token="+token,AirQuality.class);
        Cache.setAirQuality(city,air_qual);
    }

    @GetMapping("/stations")
    public Map<Integer,Station> stations(){
        return Cache.stations;
    }


}
