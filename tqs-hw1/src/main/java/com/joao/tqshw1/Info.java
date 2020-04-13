package com.joao.tqshw1;

import java.util.HashMap;

public class Info {
    //Outros valores são retornados mas não têm qualquer valor no contexto do problema
    private int aqi;
    private HashMap<String,HashMap<String,Float>> iaqi;

    //Contsrutor
    public Info(int aqi,HashMap<String,HashMap<String,Float>> iaqi){
        this.aqi = aqi;
        this.iaqi = iaqi;
    }

    //Air Quality Index Methods
    public int getAqi() {
        return aqi;
    }

    public void setAqi(int aqi) {
        this.aqi = aqi;
    }

    //IAQI Methods
    public HashMap<String, HashMap<String, Float>> getIaqi() {
        return iaqi;
    }

    public void setIaqi(HashMap<String, HashMap<String, Float>> iaqi) {
        this.iaqi = iaqi;
    }

    //toString
    @Override
    public String toString() { return "Info{" + "aqi=" + aqi + ", iaqi=" + iaqi + '}'; }
}
