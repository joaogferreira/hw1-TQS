package com.joao.tqshw1;

public class AirQuality {
    private String status;
    private Info data;
    private long time;
    private String error;

    //Construtor
    public AirQuality(String status,Info data,long time){
        this.status = status;
        this.data = data;
        this.time = time;
    }

    //Status
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){ this.status = status; }

    //Data
    public Info getData() {return data; }
    public void setData(Info data){ this.data = data; }

    //Time
    public long getTime() { return time; }
    public void setTime(long time){ this.time = time; }

    //toString
    @Override
    public String toString() {
        return "AirQuality{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", \ntime=" + time +
                '}';
    }
}
