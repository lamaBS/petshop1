package com.example.pc.petshop;

public class Seller  {
    private  String    CPassword , CEmail ,CFirstName, CLastName , uid,city;
    private  int CPoneNoumber ;
    private double XCLication;
    private double YCLocation;

    public Seller(){}

    public Seller (  String CPassword ,String  CEmail , String CFirstName,String  CLastName ,int CPoneNoumber  , String uid,String city){
        this.setCEmail( CEmail);
        this.setCFirstName( CFirstName);
        this.setCLastName(CLastName);
        this.setCPassword(CPassword) ;
        this.setCPoneNoumber( CPoneNoumber) ;
        this.setUid (uid);
        this.setcity(city);
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
    }

    public void setCFirstName(String CFirstName) {
        this.CFirstName = CFirstName;
    }

    public void setCLastName(String CLastName) {
        this.CLastName = CLastName;
    }

    public void setCPassword(String CPassword) {
        this.CPassword = CPassword;
    }

    public void setCPoneNoumber(int CPoneNoumber) {
        this.CPoneNoumber = CPoneNoumber;
    }


    public void setXCLication(double XLication) {
        this.XCLication = XLication;
    }

    public void setYCLocation(double YLocation) {
        this.YCLocation = YLocation;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public double getXCLication() {
        return XCLication;
    }

    public double getYCLocation() {
        return YCLocation;
    }

    public int getCPoneNoumber() {
        return CPoneNoumber;
    }

    public String getCEmail() {
        return CEmail;
    }

    public String getCFirstName() {
        return CFirstName;
    }

    public String getCLastName() {
        return CLastName;
    }

    public String getCPassword() {
        return CPassword;
    }
    public String getcity() {
        return city;
    }
    public void setcity(String c) {
        this.city= c;
    }
    public String getUid() {
        return uid;
    }
}

