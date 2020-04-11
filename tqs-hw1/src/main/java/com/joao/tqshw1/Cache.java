package com.joao.tqshw1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cache {
    protected static final Map<String,AirQuality> airQuality = new HashMap<>();
    protected static final Map<Integer,Station> stations = new HashMap<>();
    private static int number_Requests = 0;
    private static int miss = 0;
    private static int hit = 0;

    //Air Quality Methods
    public static void setAirQuality(String city,AirQuality airq){
        airq.setTime(System.currentTimeMillis());
        airQuality.put(city, airq);
    }
    public static Map<String,AirQuality> getAirQuality(){
        number_Requests++;
        return airQuality;
    }

    //Station Methods
    public static void setStation (int id,Station station) { stations.put(id,station); }

    public static Map<Integer,Station> getStations() {
        number_Requests++;
        return stations;
    }

    //Int Methods
    public static int countRequests(){ return number_Requests; }

    public static int getMiss() { return miss; }
    public static void incMiss() { miss++; }

    public static int getHit() { return hit; }
    public static void incHit() {  hit++; }

    //
    public boolean isValid(String city){
        long time = airQuality.get(city).getTime();

        if(System.currentTimeMillis() - time >= 600000) {
            return false;
        }

        return true;
    }

    public ArrayList<String> getCitiesAvailable(){
        ArrayList<String> aux = new ArrayList<>();
        Integer[] ids = stations.keySet().toArray(new Integer[0]);

        for(int i=0;i<ids.length;i++){
            aux.add(stations.get(ids[i]).getCity());
        }

        return aux;
    }

    public int getHitAndMiss(){
        return getHit()+getMiss();
    }

    public Station getStationByID(int id) {
        return stations.get(id);
    }

    public AirQuality getAirQualityByCity(String city){ return airQuality.get(city); }

}
