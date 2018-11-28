package com.example.pc.petshop;

class carto {
    private String id,name,buyerid,buyername,status;

    public carto() {

    }
    public carto(carto p) {
        this.status=p.status;
        this.buyerid=p.buyerid;
        this.buyername=p.buyername;
        this.id=p.id;
        this.name=p.name;

    }
    public carto(String postOwnerID, String postUsername,String buyerid) {
        this.id=postOwnerID;
        this.name=postUsername;
        this.buyerid=buyerid;
        this.buyername="buyername";
        this.status="في الانتظار";
    }
    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBuyerid() {
        return buyerid;
    }

    public void setBuyerid(String buyerid) {
        this.buyerid = buyerid;
    }

    public String getBuyername() {
        return buyername;
    }

    public void setBuyername(String buyername) {
        this.buyername = buyername;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
