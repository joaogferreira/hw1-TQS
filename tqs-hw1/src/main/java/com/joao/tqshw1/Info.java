package com.joao.tqshw1;

import java.util.HashMap;
import java.util.List;

public class Info {
    private int aqi;
    private int idx;
    private List<HashMap<String,String>>  attr;
    private HashMap<String,Object> city;
    private String dominentPol;
    private HashMap<String,HashMap<String,Integer>> iaqi;
    private HashMap<String,Object> time;

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public List<HashMap<String, String>> getAttr() {
        return attr;
    }

    public void setAttr(List<HashMap<String, String>> attr) {
        this.attr = attr;
    }

    public HashMap<String, Object> getCity() {
        return city;
    }

    public void setCity(HashMap<String, Object> city) {
        this.city = city;
    }

    public String getDominentPol() {
        return dominentPol;
    }

    public void setDominentPol(String dominentPol) {
        this.dominentPol = dominentPol;
    }

    public HashMap<String, HashMap<String, Integer>> getIaqi() {
        return iaqi;
    }

    public void setIaqi(HashMap<String, HashMap<String, Integer>> iaqi) {
        this.iaqi = iaqi;
    }

    public HashMap<String, Object> getTime() {
        return time;
    }

    public void setTime(HashMap<String, Object> time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Info{" +
                "aqi=" + aqi +
                ", idx=" + idx +
                ", attr=" + attr +
                ", city=" + city +
                ", dominentPol='" + dominentPol + '\'' +
                ", iaqi=" + iaqi +
                ", time=" + time +
                '}';
    }
}
