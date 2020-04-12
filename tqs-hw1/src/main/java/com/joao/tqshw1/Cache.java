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

    /**Air Quality Methods
     * setAirQuality - Guarda um objecto AirQuality associado a uma cidade
     * getAirQuality - Retorna um objecto AirQuality, incrementado o número de requests
     */
    public static void setAirQuality(String city,AirQuality airq){
        airq.setTime(System.currentTimeMillis());
        airQuality.put(city, airq);
    }
    public static Map<String,AirQuality> getAirQuality(){
        number_Requests++;
        return airQuality;
    }

    /** Station Methods
     * setStation - Guarda um objecto Station associado a um ID (int)
     * getStations - Retorna todos os objectos Station guardados previamente, incrementando o número de requests
     */
    public static void setStation (int id,Station station) { stations.put(id,station); }

    public static Map<Integer,Station> getStations() {
        number_Requests++;
        return stations;
    }

    //countRequests - Retorna o número de request
    public static int countRequests(){ return number_Requests; }

    //getMiss - retorna o número de tentativas SEM sucesso
    public static int getMiss() { return miss; }

    //incMiss - incrementa o número de tentativas SEM sucesso
    public static void incMiss() { miss++; }

    //getHit - retorna o número de tentativas COM sucesso
    public static int getHit() { return hit; }

    //incHit - incrementa o número de tentativas COM sucesso
    public static void incHit() {  hit++; }

    /**
    Verifica se os valores de air quality para uma determinada cidade são válidos
     São válidos se não tiverem passado 10 minutos desde o seu registo
     */
    public boolean isValid(String city){
        if(airQuality.get(city)!=null){
            long time = airQuality.get(city).getTime();
            if(System.currentTimeMillis() - time >= 600000) {
                return false;
            }
            return true;
        }
        return false;
    }

    /**
     * getCitiesAvailable - Retorna todas as cidades disponíveis
     */
    public ArrayList<String> getCitiesAvailable(){
        ArrayList<String> aux = new ArrayList<>();
        Integer[] ids = stations.keySet().toArray(new Integer[0]);

        for(int i=0;i<ids.length;i++){
            aux.add(stations.get(ids[i]).getCity());
        }

        return aux;
    }

    /**
     * getHitAndMiss - retorna a soma de Hits e Miss
     */
    public int getHitAndMiss(){
        return getHit()+getMiss();
    }

    /**
     * getStationByID - retorna um object station dado um id correspondente
     */
    public Station getStationByID(int id) {
        return stations.get(id);
    }

    /**
     * getAirQualityByCity - retorna um object AirQuality dada uma cidade correspondente
     */
    public AirQuality getAirQualityByCity(String city){ return airQuality.get(city); }

}
