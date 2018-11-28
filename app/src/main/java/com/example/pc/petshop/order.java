package com.example.pc.petshop;

public class order {
    private String ownerid,buyername,buyerid,type,price,id,img,city,username,status;
  private  int quantity;

    public int getQuantity() {
        return quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid;
    }

    public order() {}
    public order(order p) {
        this.id=p.id;
        this.type=p.type;
        this.price=p.price;
        this.ownerid=p.ownerid;
        this.img=p.img;
        this.city=p.city;
        this.status=p.status;
        this.quantity=p.quantity;
        this.buyerid=p.buyerid;
        this.buyername=p.buyername;
        this.username=p.username;


    }
    public order (String ownerid , String type, String price, String id, String img,String city,String st) {
        this.ownerid = ownerid;
        this.type = type;
        this.price = price;
        this.id = id;
        this.img=img;
        this.city=city;
        this.status=st;
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




