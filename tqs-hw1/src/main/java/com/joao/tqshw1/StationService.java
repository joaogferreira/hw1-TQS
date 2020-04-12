package com.joao.tqshw1;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class StationService {
    /** A função do StationService é interagir com a Cache protegendo-a, assim, de
     * interações feitas pela aplicação, resultantes de acções do utilizador
     */

    //Guardar objecto, interagindo com a Cache
    public void saveStation(int id, Station st) { Cache.setStation(id, st); }

    //Retorna todas as Stations, interagindo com a Cache
    public Map<Integer,Station> returnStation(){
        return Cache.getStations();
    }

    //Incrementa o valor de Hits, interagindo com a Cache
    public void incrementHit() { Cache.incHit(); }

    //Incrementa o valor de Miss, interagindo com a Cache
    public void incrementMiss() { Cache.incMiss(); }

    //Retorna o valor de Hit, interagindo com a Cache
    public int getHit() { return Cache.getHit(); }

    //Retorna o valor de Miss, interagindo com a Cache
    public int getMiss() { return Cache.getMiss(); }

}