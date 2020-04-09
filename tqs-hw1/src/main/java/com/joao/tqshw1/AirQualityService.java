package com.joao.tqshw1;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class AirQualityService {
    public void saveAirQuality(String city,AirQuality aq) {
        Cache.setAirQuality(city,aq);
    }

    public Map<String,AirQuality> returnAirQuality(){
        return Cache.getAirQuality();
    }

    public void incrementHit() { }

    public void incrementMiss() { }
}
