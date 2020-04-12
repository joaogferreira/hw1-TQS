package com.joao.tqshw1;

public class Station {
    private String city;
    private int id;

    //Construtor
    public Station(int id, String city){ this.id = id; this.city = city; }

    //City Methods
    public String getCity(){ return city; }
    public void setCity(String new_city) { this.city = new_city; }

    //ID Methods
    public int getID() { return id; }
    public void setID(int id){ this.id = id; }

    //toString
    @Override
    public String toString() { return "Station{" + "city='" + city + '\'' +  '}'; }
}
