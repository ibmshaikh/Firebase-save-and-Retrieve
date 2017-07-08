package com.google.firebase.udacity.Model_class;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by siddique on 6/12/2017.
 */

public class group {
    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    private String chat;

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    private String Sender;



    public String getUsname() {
        return usname;
    }

    public void setUsname(String usname) {
        this.usname = usname;
    }

    private String usname;


    public group() {
      /*Blank default constructor essential for Firebase*/
    }


    public group(String chat,String sender){
        this.chat=chat;
        this.Sender=sender;

    }



}