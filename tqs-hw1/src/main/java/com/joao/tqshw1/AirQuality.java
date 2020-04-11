package com.joao.tqshw1;

public class AirQuality {
    private String status;
    private Info data;
    private long time;

    public AirQuality(String status,Info data,long time){
        this.status = status;
        this.data = data;
        this.time = time;
    }

    public String getStatus(){
        return status;
    }
    public void setStatus(String status){ this.status = status; }

    public Info getData() {return data; }
    public void setData(Info data){ this.data = data; }

    public long getTime() { return time; }
    public void setTime(long time){ this.time = time; }

    @Override
    public String toString() {
        return "AirQuality{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", \ntime=" + time +
                '}';
    }
}
