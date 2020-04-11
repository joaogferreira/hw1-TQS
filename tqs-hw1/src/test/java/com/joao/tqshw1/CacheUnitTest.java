package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(before_size+1==after_size).isEqualTo(true);
        assertThat(before_size==after_size).isEqualTo(false);

        //Check if AirQuality objects are equal
        Map<String,AirQuality> aq = Cache.getAirQuality();
        assertThat(aq.get("Porto").equals(airQuality)).isEqualTo(true);
    }

    @Test
    public void testGetAirQuality() {
        Cache.setAirQuality("Aveiro",airQuality);
        old_req = Cache.countRequests();
        Map <String,AirQuality> aq = Cache.getAirQuality();
        new_req = Cache.countRequests();

        //Number of requests
        assertThat(old_req==new_req).isEqualTo(false);
        assertThat(old_req+1==new_req).isEqualTo(true);

        assertThat(aq.equals(null)).isEqualTo(false);
        assertThat(airQuality.equals(aq.get("Aveiro"))).isEqualTo(true);


    }

    @Test
    public void testSetStation() {
        station = new Station(1,"Coimbra");
        before_size = Cache.getStations().size();
        Cache.setStation(station.getID(),station);
        after_size = Cache.getStations().size();

        //Number of requests
        assertThat(before_size+1==after_size).isEqualTo(true);
        assertThat(before_size==after_size).isEqualTo(false);

        //Check if Station object fields are equals
        Map<Integer,Station> st = Cache.getStations();
        assertThat(1==st.get(1).getID()).isEqualTo(true);
        assertThat("Coimbra".equals(st.get(1).getCity())).isEqualTo(true);

    }
    @Test
    public void testGetStation() {
        station = new Station(2,"Bragança");
        Cache.setStation(station.getID(),station);
        old_req = Cache.countRequests();
        Map<Integer,Station> st = Cache.getStations();
        new_req = Cache.countRequests();

        assertThat(st.equals(null)).isEqualTo(false);
        assertThat(station.equals(st.get(2))).isEqualTo(true);

        assertThat(old_req==new_req).isEqualTo(false);
        assertThat(old_req+1==new_req).isEqualTo(true);
    }

    @Test
    public void testCountRequests() {
        old_req = Cache.countRequests();
        Cache.getAirQuality();
        new_req = Cache.countRequests();

        assertThat(old_req+1==new_req).isEqualTo(true);
        assertThat(old_req==new_req).isEqualTo(false);
    }


}
