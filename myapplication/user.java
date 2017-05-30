package com.example.siddique.myapplication;

/**
 * Created by siddique on 5/30/2017.
 */

public class user {
    private String name;
    public user() {
      /*Blank default constructor essential for Firebase*/
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
