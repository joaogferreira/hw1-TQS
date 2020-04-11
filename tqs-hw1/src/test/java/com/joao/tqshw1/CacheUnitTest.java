package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class CacheUnitTest {
    //Unit Test - JUNIT
    private AirQuality airQuality;
    private Info info;
    private String status,city;
    private long time;
    private Station station;
    private int id, before_size, after_size, old_req, new_req, size1, size2;

    Map<String,AirQuality> airQualityMap = Cache.getAirQuality();
    Map<Integer,Station> stationMap = Cache.getStations();

    @Before
    public void prepare() {
        HashMap<String, HashMap<String,Float>> info_example = new HashMap<>();

        HashMap<String,Float> toPutInInfo1 = new HashMap<>();
        HashMap<String,Float> toPutInInfo2 = new HashMap<>();

        toPutInInfo1.put("v", (float) 2.0);
        toPutInInfo2.put("v", (float) 17.4);

        info_example.put("co",toPutInInfo1);
        info_example.put("pm25",toPutInInfo2);

        Info info = new Info(20,info_example);

        time = new Timestamp(System.currentTimeMillis()).getTime();

        airQuality = new AirQuality("ok",info,time);

    }

    @Test
    public void testSetAirQuality(){
        before_size = airQualityMap.size();
        Cache.setAirQuality("Porto",airQuality);
        after_size = airQualityMap.size();

        //Assert size before and after
        Assertions.assertEquals(before_size+1,after_size);
        Assertions.assertNotEquals(before_size,after_size);

        //Check if AirQuality objects are equal
        Map<String,AirQuality> aq = Cache.getAirQuality();
        Assertions.assertEquals(aq.get("Porto"),airQuality);
    }

    @Test
    public void testGetAirQuality() {
        Cache.setAirQuality("Aveiro",airQuality);
        old_req = Cache.countRequests();
        Map <String,AirQuality> aq = Cache.getAirQuality();
        new_req = Cache.countRequests();

        //Number of requests
        Assertions.assertNotEquals(old_req,new_req);
        Assertions.assertEquals(old_req+1,new_req);

        Assertions.assertNotNull(aq);
        Assertions.assertEquals(airQuality,aq.get("Aveiro"));

    }

    @Test
    public void testSetStation() {
        station = new Station(1,"Coimbra");
        before_size = Cache.getStations().size();
        Cache.setStation(station.getID(),station);
        after_size = Cache.getStations().size();

        //Number of requests
        Assertions.assertEquals(before_size+1,after_size);
        Assertions.assertNotEquals(before_size,after_size);

        //Check if Station object fields are equals
        Map<Integer,Station> st = Cache.getStations();
        Assertions.assertEquals(1,st.get(1).getID());
        Assertions.assertEquals("Coimbra",st.get(1).getCity());
    }
    @Test
    public void testGetStation() {
        station = new Station(2,"Bragança");
        Cache.setStation(station.getID(),station);
        old_req = Cache.countRequests();
        Map<Integer,Station> st = Cache.getStations();
        new_req = Cache.countRequests();

        Assertions.assertNotNull(st);
        Assertions.assertEquals(station, st.get(2));

        Assertions.assertNotEquals(old_req,new_req);
        Assertions.assertEquals(old_req+1,new_req);
    }

    @Test
    public void testCountRequests() {
        old_req = Cache.countRequests();
        Cache.getAirQuality();
        new_req = Cache.countRequests();

        Assertions.assertEquals(old_req+1,new_req);
        Assertions.assertNotEquals(old_req,new_req);
    }


}
