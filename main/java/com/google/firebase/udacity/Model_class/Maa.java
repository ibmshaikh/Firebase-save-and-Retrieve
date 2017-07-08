package com.google.firebase.udacity.Model_class;

/**
 * Created by siddique on 6/12/2017.
 */

public class Maa {
    public String getNnmm() {
        return nnmm;
    }

    public void setNnmm(String nnmm) {
        this.nnmm = nnmm;
    }

    private String nnmm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    public String getQery() {
        return qery;
    }

    public void setQery(String qery) {
        this.qery = qery;
    }

    private String qery;


    public Maa() {
      /*Blank default constructor essential for Firebase*/
    }
    public Maa(String nnmm,String id,String qery){
        this.nnmm=nnmm;
        this.id=id;
        this.qery=qery;

    }






}