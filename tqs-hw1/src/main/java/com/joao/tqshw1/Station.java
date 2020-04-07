package com.joao.tqshw1;

public class Station {
    private String city;
    private int id;

    public Station(int id, String city){ this.id = id; this.city = city; }

    public String getCity(){ return city; }
    public void setCity(String new_city) { this.city = new_city; }

    public int getID() { return id; }
    public void setID(int id){ this.id = id; }

    @Override
    public String toString() { return "Station{" + "city='" + city + '\'' +  '}'; }
}
