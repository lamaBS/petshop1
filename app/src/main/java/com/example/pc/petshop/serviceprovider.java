package com.example.pc.petshop;

class serviceprovider {

    private  String    CPassword , CEmail ,CFirstName, CLastName , uid,city,serv;
    private  int CPoneNoumber ;
    private double XCLication;
    private double YCLocation;
    public serviceprovider(){}

    public serviceprovider (String CPassword , String  CEmail , String CFirstName, String  CLastName , int CPoneNoumber  , String uid, String city, String s){
        this.setCEmail( CEmail);
        this.setCFirstName( CFirstName);
        this.setCLastName(CLastName);
        this.setServ(s);
        this.setCPassword(CPassword) ;
        this.setCPoneNoumber( CPoneNoumber) ;
        this.setUid (uid);
        this.setcity(city);
    }
    public String getCPassword() {
        return CPassword;
    }
    public String getServ() {
        return serv;
    }

    public void setServ(String serv) {
        this.serv = serv;
    }
    public void setCPassword(String CPassword) {
        this.CPassword = CPassword;
    }

    public String getCEmail() {
        return CEmail;
    }

    public void setCEmail(String CEmail) {
        this.CEmail = CEmail;
    }

    public String getCFirstName() {
        return CFirstName;
    }

    public void setCFirstName(String CFirstName) {
        this.CFirstName = CFirstName;
    }

    public String getCLastName() {
        return CLastName;
    }

    public void setCLastName(String CLastName) {
        this.CLastName = CLastName;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getCPoneNoumber() {
        return CPoneNoumber;
    }

    public void setCPoneNoumber(int CPoneNoumber) {
        this.CPoneNoumber = CPoneNoumber;
    }

    public double getXCLication() {
        return XCLication;
    }

    public void setXCLication(double XCLication) {
        this.XCLication = XCLication;
    }

    public double getYCLocation() {
        return YCLocation;
    }
    public String getcity() {
        return city;
    }
    public void setcity(String c) {
        this.city= c;
    }
    public void setYCLocation(double YCLocation) {
        this.YCLocation = YCLocation;
    }

}
