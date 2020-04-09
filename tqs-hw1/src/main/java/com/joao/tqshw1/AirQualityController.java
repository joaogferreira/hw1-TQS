package com.joao.tqshw1;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
            this.refresh(city);
        }
        if(System.currentTimeMillis() - service_air.returnAirQuality().get(city).getTime() > 10000) {
            this.refresh(city);
        }
        return service_air.returnAirQuality().get(city);
    }

    private void refresh(String city){
        AirQuality air_quality = restTemplate.getForObject("https://api.waqi.info/feed/"+city+"/?token="+token,AirQuality.class);
        service_air.saveAirQuality(city,air_quality);
    }


    @GetMapping("/stations")
    public Map<Integer,Station> stations(){
        return service_station.returnStation();
    }

    @RequestMapping("/error")
    @ResponseBody
    public String handleError() {
        return String.format("<html><body><h2>Error Page</h2>");
    }
}
