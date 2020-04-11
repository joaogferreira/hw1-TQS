package com.joao.tqshw1;

import org.junit.Before;
import org.junit.Test;


import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

public class CacheUnitTest {
    //Unit Test - JUNIT
    private AirQuality airQuality;
    private Info info;
    private String status,city;
    private long time;
    private Station station;
    private int id, before_size, after_size, old_req, new_req, size1, size2;
    Cache cache;

    Map<String,AirQuality> airQualityMap = Cache.getAirQuality();
    Map<Integer,Station> stationMap = Cache.getStations();

    @Before
    public void prepare() {
        cache = new Cache();
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
        cache.setAirQuality("Porto",airQuality);
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
        cache.setAirQuality("Aveiro",airQuality);
        old_req = cache.countRequests();
        Map <String,AirQuality> aq = cache.getAirQuality();
        new_req = cache.countRequests();

        //Number of requests
        assertThat(old_req==new_req).isEqualTo(false);
        assertThat(old_req+1==new_req).isEqualTo(true);

        assertThat(aq.equals(null)).isEqualTo(false);
        assertThat(airQuality.equals(aq.get("Aveiro"))).isEqualTo(true);


    }

    @Test
    public void testSetStation() {
        station = new Station(100,"Coimbra");
        before_size = cache.getStations().size();
        cache.setStation(station.getID(),station);
        after_size = cache.getStations().size();

        //Assert Size
        assertThat(before_size+1==after_size).isEqualTo(true);
        assertThat(before_size==after_size).isEqualTo(false);

        //Check if Station object fields are equals
        Map<Integer,Station> st = cache.getStations();
        assertThat(100==st.get(100).getID()).isEqualTo(true);
        assertThat("Coimbra".equals(st.get(100).getCity())).isEqualTo(true);

    }
    @Test
    public void testGetStation() {
        station = new Station(2,"Bragança");
        cache.setStation(station.getID(),station);
        old_req = cache.countRequests();
        Map<Integer,Station> st = cache.getStations();
        new_req = cache.countRequests();

        assertThat(st.equals(null)).isEqualTo(false);
        assertThat(station.equals(st.get(2))).isEqualTo(true);

        assertThat(old_req==new_req).isEqualTo(false);
        assertThat(old_req+1==new_req).isEqualTo(true);
    }

    @Test
    public void testCountRequests() {
        old_req = cache.countRequests();
        cache.getAirQuality();
        new_req = cache.countRequests();

        assertThat(old_req+1==new_req).isEqualTo(true);
        assertThat(old_req==new_req).isEqualTo(false);
    }

    @Test
    public void testIsValid(){
        cache.setAirQuality("Cannes",airQuality);
        cache.getAirQualityByCity("Cannes").setTime(System.currentTimeMillis());
        assertThat(cache.isValid("Cannes")).isEqualTo(true);

        cache.setAirQuality("Houston",airQuality);
        cache.getAirQualityByCity("Houston").setTime(0);
        assertThat(cache.isValid("Houston")).isEqualTo(false);
    }

    @Test
    public void testGetCitiesAvailable(){
        cache.setStation(1,new Station(6,"New York"));
        cache.setStation(2,new Station(7,"Trofa"));

        assertThat(cache.getCitiesAvailable().contains("New York")).isEqualTo(true);
        assertThat(cache.getCitiesAvailable().contains("Trofa")).isEqualTo(true);
        assertThat(cache.getCitiesAvailable().contains("Guarda")).isEqualTo(false);
    }

    @Test
    public void testHitAndMiss(){
        for(int i=0;i<200;i++){ cache.incMiss(); }
        for(int j=0;j<11;j++){ cache.incHit(); }

        assertThat(cache.getHitAndMiss()==211).isEqualTo(true);
        assertThat(cache.getHit()==11).isEqualTo(true);
        assertThat(cache.getMiss()==200).isEqualTo(true);
    }

    @Test
    public void testGetStationByID(){
        cache.setStation(23,new Station(23,"Los Angeles"));

        int wrong_id = 999;

        assertThat(cache.getStationByID(23).getCity().equals("Los Angeles")).isEqualTo(true);
        assertThat(cache.getStationByID(23).getID()==23).isEqualTo(true);
        assertThat(cache.getStationByID(wrong_id)).isEqualTo(null);
    }

    @Test
    public void testGetAirQualityByCity() throws InterruptedException {
        HashMap<String, HashMap<String,Float>> info_example = new HashMap<>();

        HashMap<String,Float> toPutInInfo1 = new HashMap<>();
        HashMap<String,Float> toPutInInfo2 = new HashMap<>();

        toPutInInfo1.put("v", (float) 1.0);
        toPutInInfo2.put("v", (float) 34.56);

        info_example.put("co",toPutInInfo1);
        info_example.put("pm25",toPutInInfo2);

        Info info = new Info(27,info_example);

        time = new Timestamp(System.currentTimeMillis()).getTime();
        airQuality = new AirQuality("ok",info,time);
        cache.setAirQuality("Portalegre",airQuality);

        AirQuality airQualityReceived = cache.getAirQualityByCity("Portalegre");
        assertThat(airQualityReceived.getTime()>=time).isEqualTo(true); //o tempo de registo pode ser ligeiramente superior
        assertThat(airQualityReceived.getStatus().equals("ok"));
        assertThat(airQualityReceived.getData().equals(info));
    }
}
