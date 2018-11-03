package com.example.anthony.myapplication;

public class User {

    //this is a simple user class for db
    private String name, uname, pass;

    /*public User(int id, String name, String uname, String pass) {
        this.id = id;
        this.name = name;
        this.uname = uname;
        this.pass = pass;
    }*/

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









}
