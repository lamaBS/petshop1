package com.example.pc.petshop;

public class Supplies {
    private String ownerid,name,type,price,id,img,city,username;

    public Supplies() {}
    public Supplies(Supplies p) {
        this.id=p.id;
        this.type=p.type;
        this.price=p.price;
        this.ownerid=p.ownerid;
        this.img=p.img;
        this.city=p.city;


    }
    public Supplies (String ownerid , String type, String price, String id, String img,String city) {
        this.ownerid = ownerid;
        this.type = type;
        this.price = price;
        this.id = id;
        this.img=img;
        this.city=city;
    }
    public String getCity() {
        return city;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    public String getOwnerid() {
        return ownerid;
    }

    public void setOwnerid(String ownerid) {
        this.ownerid = ownerid;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}




