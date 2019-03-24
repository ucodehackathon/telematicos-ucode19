package com.example;

import com.example.Race;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

class User implements Serializable {
    private List<Race> races;
    private List<String> friends;
    private final String USR_ID;
    private String name;
    private String gender;
    private String age;
    private String weight;
    private String location;
    private String email;
    private String token;
    private String password;


    public User(String USR_ID, String password, String name, String gender, String age, String weight, String email, String token) {
        this.races = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.USR_ID = USR_ID;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.weight = weight;
        this.email = email;
        this.token = token;
        this.password = password;
    }

    public String getName(){
        return name;
    }
    public String getUSR_ID(){
        return USR_ID;
    }
    public String getGender(){
        return gender;
    }
    public String getAge(){
         return age;
    }
    public String getWeight(){
        return weight;
    }
    public Race getRace(int ID){
        return races.get(ID);
    }
    public List<String> getFriends(){
        return friends;
    }
    public void addRace(Race race){
        this.races.add(race);
    }
    public String getEmail(){return email;}
    public String getLocation(){return location;}
    public String getToken(){return token;}
    public String getPassword(){return password;}
    public void addFriend(String friendID){
        this.friends.add(friendID);
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {this.name=name;}

    public void setEmail(String email) {this.email=email;}

    public void setPassword(String password) { this.password=password;}
}
