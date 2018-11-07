package com.example.pc.petshop;

public class Hotel {

    private String name , address , price , details , ID ;
    public Hotel() {}
    public Hotel(Hotel h) {
        this.name=h.name ;
        this.address=h.address ;
        this.price=h.price ;
        this.details=h.details ;
        this.ID=h.ID ;

    }
    public Hotel(String name, String address, String price, String details, String ID) {
        this.name=name ;
        this.address=address ;
        this.price=price ;
        this.details=details ;
        this.ID=ID ;}

    public String getPrice() {
        return price;
    }
    public String getName() {
        return name ;
    }
    public String getAddress() {
        return address;
    }
    public String getDetails() {
        return details;
    }
    public String getID() {
        return ID;
    }
    public void setPrice(String price) { this.price = price; }
    public void setName(String name) {
        this.name = name;
    }
    public void setAddress(String address) { this.address = address; }
    public void setDetails(String details) { this.details = details; }
    public void setID(String ID) { this.ID = ID; }

}
