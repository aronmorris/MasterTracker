package com.example.anthony.myapplication;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    //this is a simple user class for db
    private String name;
    private String uname;
    private String pass;
    private String endomodoname;
    private String endomondopass;
    private ArrayList<Double> avgspeeds;
    private ArrayList<Double> durations;
    private ArrayList<Double> distances;


    public User() {
        this.name ="Default";
        this.uname = "Default";
        avgspeeds = new ArrayList<Double>();
        durations = new ArrayList<Double>();
        distances = new ArrayList<Double>();
        avgspeeds.add(1.00);
        durations.add(1.00);
        distances.add(1.00);
    }
    public User( String name, String uname) {
        this.name = name;
        this.uname = uname;
        avgspeeds = new ArrayList<Double>();
        durations = new ArrayList<Double>();
        distances = new ArrayList<Double>();
        avgspeeds.add(1.00);
        durations.add(1.00);
        distances.add(1.00);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
    public String getUname() {
        return uname;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
    public String getPass() {
        return pass;
    }
    public String getEndomodoname() {
        return endomodoname;
    }

    public void setEndomodoname(String endomodoname) {
        this.endomodoname = endomodoname;
    }

    public String getEndomondopass() {
        return endomondopass;
    }

    public void setEndomondopass(String endomondopass) {
        this.endomondopass = endomondopass;
    }
    public ArrayList<Double> getAvgspeeds() {
        return avgspeeds;
    }

    public void setAvgspeeds(ArrayList<Double> avgspeeds) {
        this.avgspeeds = avgspeeds;
    }

    public ArrayList<Double> getDurations() {
        return durations;
    }

    public void setDurations(ArrayList<Double> durations) {
        this.durations = durations;
    }

    public ArrayList<Double> getDistances() {
        return distances;
    }

    public void setDistances(ArrayList<Double> distances) {
        this.distances = distances;
    }








}
