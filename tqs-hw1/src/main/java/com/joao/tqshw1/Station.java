package com.joao.tqshw1;

public class Station {
    private float lat;
    private float lon;
    private Long uid;
    private Integer aqi;

    protected float getLat() {
        return lat;
    }

    protected void setLat(float lat) {
        this.lat = lat;
    }

    protected float getLon() {
        return lon;
    }

    protected void setLon(float lon) {
        this.lon = lon;
    }

    protected Long getUid() {
        return uid;
    }

    protected void setUid(Long uid) {
        this.uid = uid;
    }

    protected Integer getAqi() {
        return aqi;
    }

    protected void setAqi(Integer aqi) {
        this.aqi = aqi;
    }

    @Override
    public String toString() {
        return "Station [lat=" + lat + ", lon=" + lon + ", uid=" + uid
                + ", aqi=" + aqi + "]";
    }
}
