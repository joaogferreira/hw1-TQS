package com.joao.tqshw1;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    protected static final Map<String,AirQuality> airQuality = new HashMap<>();
    protected static final Map<Integer,Station> stations = new HashMap<>();
    private static int number_Requests = 0;

    public static void setAirQuality(String city,AirQuality airq){
        airq.setTime(System.currentTimeMillis());
        airQuality.put(city, airq);
        number_Requests++;
    }
    public static Map<String,AirQuality> getAirQuality(){
        return airQuality;
    }


    public static void setStation (int id,Station station) {
        stations.put(id,station);
        number_Requests++;
    }
    public static Map<Integer,Station> getStations() { return stations; }

    public static int countRequests(){ return number_Requests; }

    public static int getAirQualityCurrentSize() { return airQuality.size(); }

    public static int getStationsCurrentSize() { return stations.size(); }
}
