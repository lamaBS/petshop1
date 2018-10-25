package com.example.pc.petshop;


public class suggestion  {
    private String sugges, id;
    public suggestion() {
    }
    public suggestion(String suggest1 , String id1 ) {
        this.sugges = suggest1;
        this.id = id1;
    }


    public suggestion(suggestion value) {
        this.sugges = value.getSuggest();
        this.id= value.getID();
    }

    public String getSuggest() {
        return sugges;
    }

    public void setSuggest(String suggest) {
        this.sugges = suggest;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

}
