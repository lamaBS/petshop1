package com.example.pc.petshop;

public class Pet {

    private String ownerid,age,type,price,id,img,city;
    public Pet() {}
    public Pet(Pet p) {
        this.age=p.age;
        this.id=p.id;
        this.type=p.type;
        this.price=p.price;
        this.ownerid=p.ownerid;
        this.img=p.img;

    }
    public Pet(String ownerid, String age, String type, String price, String id, String img,String c) {
        this.ownerid = ownerid;
        this.age = age;
        this.type = type;
        this.price = price;
        this.id = id;
        this.img=img;
        this.city=c;
    }

    public String getCity() {
        return city;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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
