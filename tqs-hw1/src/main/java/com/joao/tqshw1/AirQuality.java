package com.joao.tqshw1;

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
