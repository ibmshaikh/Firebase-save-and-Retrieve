package com.example.siddique.phoneauth;

/**
 * Created by siddique on 6/11/2017.
 */
public class user {
    private String name;
    private String id;

    public user() {
      /*Blank default constructor essential for Firebase*/
    }


    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }
    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }


}