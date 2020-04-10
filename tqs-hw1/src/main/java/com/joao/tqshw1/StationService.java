package com.joao.tqshw1;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;


@Service
public class StationService {
    public void saveStation(int id, Station st) { Cache.setStation(id, st); }

    public Map<Integer,Station> returnStation(){
        return Cache.getStations();
    }

    public void incrementHit() { Cache.incHit(); }

    public void incrementMiss() { Cache.incMiss(); }


    public Object[] getStationDetails(int id){
        Map<Integer,Station> stations = returnStation();
        Object[] res = new String[2];
        System.out.println(stations.get(id));
        res[0] = stations.get(id).getID();
        res[1] = stations.get(id).getCity();
        return res;
    }
}