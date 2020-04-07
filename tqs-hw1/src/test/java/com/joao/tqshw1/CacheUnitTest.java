package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;
import org.junit.*;
import org.junit.jupiter.api.Assertions;

import java.util.Map;

public class CacheUnitTest {
    private AirQuality airQuality;
    private Info info;
    private String status;
    private long time;
    private String city;
    private Station station;
    private int id;
    private int expected_size;
    private int old_req;
    private int new_req;

    @Before
    public void prepare() {
        airQuality = new AirQuality();

        time = 0;
        airQuality.setTime(time);

        status = "OK";
        airQuality.setStatus(status);

        info = new Info();
        airQuality.setData(info);

        city = "Porto";
        id=1;
        station = new Station(id,city);

        expected_size=1;
        old_req=0;
        new_req=0;
    }

    @Test
    public void testSetAirQuality(){
        old_req = Cache.countRequests();
        Cache.setAirQuality(city,airQuality);
        new_req = Cache.countRequests();

        //Number of requests
        Assertions.assertEquals(old_req+1,new_req);

        //Check if AirQuality object fields are equal
        Map<String,AirQuality> aq = Cache.getAirQuality();
        Assertions.assertEquals(info,aq.get(city).getData());
        Assertions.assertEquals(status,aq.get(city).getStatus());
        Assertions.assertTrue(aq.get(city).getTime()>time);
    }

    @Test
    public void testGetAirQuality() {
        Cache.setAirQuality(city,airQuality);
        Map<String,AirQuality> aq = Cache.getAirQuality();
        Assertions.assertEquals(expected_size,aq.size());
    }

    @Test
    public void testSetStation() {
        old_req = Cache.countRequests();
        Cache.setStation(station.getID(),station);
        new_req = Cache.countRequests();

        //Number of requests
        Assertions.assertEquals(old_req+1,new_req);

        //Check if Station object fields are equals
        Map<Integer,Station> st = Cache.getStations();
        Assertions.assertEquals(id,st.get(id).getID());
        Assertions.assertEquals(city,st.get(id).getCity());
    }
    @Test
    public void testGetStation() {
        Cache.setStation(station.getID(),station);
        Map<Integer,Station> st = Cache.getStations();
        Assertions.assertEquals(expected_size,st.size());
        Assertions.assertEquals(station, Cache.getStations().get(id));
    }

    @Test
    public void testCountRequests() {
        old_req = Cache.countRequests();
        Cache.setAirQuality(city,airQuality);
        new_req = Cache.countRequests();
        Assertions.assertEquals(old_req+1,new_req);
    }


}
