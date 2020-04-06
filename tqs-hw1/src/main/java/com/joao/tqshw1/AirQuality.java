package com.joao.tqshw1;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQuality {
    private String status;
    private Info data;
    private long time;

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){ this.status = status; }

    public Info getData() {return data; }
    public void setData(Info data){ this.data = data; }

    public long getTime() { return time; }
    public void setTime(long time){ this.time = time; }

    public String toString(){
        return "AirQuality{" + "status=" + status +  " time=" + time + '}';
    }

}
