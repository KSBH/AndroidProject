package com.example.karim.autochecketmat;

import android.util.Log;

import java.util.UUID;

/**
 * Created by Karim on 30-12-16.
 */

public class Users {


    String username;
    String password;
    String name;
    String fristname;
    String id;
    boolean isactive;


    public boolean getIsactive() {
        return isactive;
    }

    public void setIsactive() {
        this.isactive = true;
    }

    public void setId(String id){
        //UUID uid = UUID.randomUUID();
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return username;
    }
    public void setName(String name){

        this.name = name;

    }
    public String getName(){

        return name;

    }
    public void setFristname(String fristname){

        this.fristname = fristname;

    }
    public String getFristName(){

        return fristname;

    }
    public void setPassword(String password){

        this.password = password;

    }

    public String getPassword() {

        return password;

    }

}
