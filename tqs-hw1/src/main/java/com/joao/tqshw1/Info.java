package com.joao.tqshw1;

import java.util.HashMap;

public class Info {
    //Outros valores são retornados mas não têm qualquer valor no contexto do problema
    private int aqi;
    private HashMap<String,HashMap<String,Integer>> iaqi;

    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    public HashMap<String, HashMap<String, Integer>> getIaqi() {
        return iaqi;
    }

    public void setIaqi(HashMap<String, HashMap<String, Integer>> iaqi) {
        this.iaqi = iaqi;
    }

    @Override
    public String toString() { return "Info{" + "aqi=" + aqi + ", iaqi=" + iaqi + '}'; }
}
