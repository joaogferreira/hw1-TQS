package com.joao.tqshw1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class AirQualityController {

    @Autowired
    private AirQualityService service_air;

    @Autowired
    private StationService service_station;

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

    /**
    @GetMapping("/stations")
    public Map<Integer,Station> stations(){
        return service_station.returnStation();
    }
    */

    @GetMapping("/stations")
    public String stations(){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(service_station.returnStation());
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }


}
