package com.joao.tqshw1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class AirQualityServiceTest {

    @Mock (lenient = true)
    private Cache cache;

    @InjectMocks
    private AirQualityService airQualityService;

    ArrayList<String> citiesAvailable = new ArrayList<>();

    @BeforeEach
    public void setup() throws Exception {
        String[] cities = {"Las Vegas","Ibiza","Miami","Amsterdam","Bangkok"};
        for (String city : cities)
            citiesAvailable.add(city);


        int miss = 75;
        int hit = 200;

        HashMap<String,HashMap<String,Float>> info_example = new HashMap<>();

        HashMap<String,Float> toPutInInfo1 = new HashMap<>();
        HashMap<String,Float> toPutInInfo2 = new HashMap<>();

        toPutInInfo1.put("v", (float) 2.0);
        toPutInInfo2.put("v", (float) 17.4);

        info_example.put("co",toPutInInfo1);
        info_example.put("pm25",toPutInInfo2);

        Info info = new Info(20,info_example);

        long timestamp = new Timestamp(System.currentTimeMillis()).getTime();

        AirQuality airquality_ibiza = new AirQuality("ok",info,timestamp);

        Mockito.when(cache.getCitiesAvailable()).thenReturn(citiesAvailable);
        Mockito.when(cache.getHitAndMiss()).thenReturn(hit+miss);
        Mockito.when(cache.getAirQualityByCity("Ibiza")).thenReturn(airquality_ibiza);
        Mockito.when(cache.getAirQualityByCity("Vigo")).thenReturn(null);
    }

    @Test
    public void validCity(){
        for(int i=0;i<citiesAvailable.size();i++){
            assertThat(cache.isValid(citiesAvailable.get(i)));
        }
    }
}
