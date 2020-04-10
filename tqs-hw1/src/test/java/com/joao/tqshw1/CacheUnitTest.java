package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class CacheUnitTest {
    //Unit Test - JUNIT
    private AirQuality airQuality;
    private Info info;
    private String status;
    private long time;
    private String city;
    private Station station;
    private int id;
    private int before_size;
    private int after_size;
    private int old_req;
    private int new_req;
    private int size1;
    private int size2;
    Map<String,AirQuality> airQualityMap = Cache.getAirQuality();
    Map<Integer,Station> stationMap = Cache.getStations();

    @Before
    public void prepare() {
        airQuality = new AirQuality();
        time = System.currentTimeMillis();
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
