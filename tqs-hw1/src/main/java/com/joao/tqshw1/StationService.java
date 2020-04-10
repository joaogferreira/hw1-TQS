package com.joao.tqshw1;

import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class StationService {
    public void saveStation(int id, Station st) { Cache.setStation(id, st); }

    public Map<Integer,Station> returnStation(){
        return Cache.getStations();
    }

    public void incrementHit() { Cache.incHit(); }

    public void incrementMiss() { Cache.incMiss(); }
}