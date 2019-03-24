package com.example;

import java.io.Serializable;

class Race implements Serializable {
    private final String ID;
    private String user_name;
    private String km;
    private String time;
    private String race;
    private String spot;
    private String date;
    //private String age;

    public Race(String ID, String user_name, String race, String km, String spot, String time ,String date){
        this.ID = ID;
        this. user_name = user_name;
        this.race = race;
        this.spot = spot;
        this.km = km;
        this.time = time;
        this.date = date;
    }
    public String getID(){
        return ID;
    }
    public String getUser_name(){ return user_name; }
    public String getKm(){
        return km;
    }
    public String getTime(){
        return time;
    }
    public String getDate(){
        return date;
    }
    public String getSpot(){
        return spot;
    }
    public String getRace(){
        return race;
    }


}
