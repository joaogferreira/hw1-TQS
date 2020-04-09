package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;
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
    private int before_size;
    private int after_size;
    private int old_req;
    private int new_req;
    private int size1;
    private int size2;

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

        old_req=0;
        new_req=0;
    }

    @Test
    public void testSetAirQuality(){
        before_size = Cache.getAirQuality().size();
        Cache.setAirQuality(city,airQuality);

        after_size = Cache.getAirQuality().size();

        //Assert size before and after
        //Assertions.assertEquals(before_size+1,after_size);
        //Assertions.assertNotEquals(before_size,after_size);

        //Check if AirQuality object fields are equal
        Map<String,AirQuality> aq = Cache.getAirQuality();
        Assertions.assertEquals(info,aq.get(city).getData());
        Assertions.assertEquals(status,aq.get(city).getStatus());
        Assertions.assertTrue(aq.get(city).getTime()>time);
    }

    @Test
    public void testGetAirQuality() {
        //Feito
        Cache.setAirQuality(city,airQuality);
        old_req = Cache.countRequests();
        Map <String,AirQuality> aq = Cache.getAirQuality();
        new_req = Cache.countRequests();

        //Number of requests
        Assertions.assertNotEquals(old_req,new_req);
        Assertions.assertEquals(old_req+1,new_req);

        Assertions.assertNotNull(aq);
        Assertions.assertEquals(airQuality,aq.get(city));

    }

    @Test
    public void testSetStation() {

        before_size = Cache.getStations().size();
        Cache.setStation(station.getID(),station);
        after_size = Cache.getStations().size();

        //Number of requests
        Assertions.assertEquals(before_size+1,after_size);
        Assertions.assertNotEquals(before_size,after_size);

        //Check if Station object fields are equals
        Map<Integer,Station> st = Cache.getStations();
        Assertions.assertEquals(id,st.get(id).getID());
        Assertions.assertEquals(city,st.get(id).getCity());
    }
    @Test
    public void testGetStation() {
        Cache.setStation(station.getID(),station);
        old_req = Cache.countRequests();
        Map<Integer,Station> st = Cache.getStations();
        new_req = Cache.countRequests();

        Assertions.assertNotNull(st);
        Assertions.assertEquals(station, st.get(id));

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
