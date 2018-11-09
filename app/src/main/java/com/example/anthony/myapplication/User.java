package com.example.anthony.myapplication;

import java.io.Serializable;

public class User implements Serializable {

    //this is a simple user class for db
    private String name;
    private String uname;
    private String pass;
    private String endomodoname;
    private String endomondopass;


    public User() {
        this.name ="Default";
        this.uname = "Default";
    }
    public User( String name, String uname) {
        this.name = name;
        this.uname = uname;
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








}
