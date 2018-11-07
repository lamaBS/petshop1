package com.example.pc.petshop;

/**
 * Created by wafa0 on 19/03/18.
 */

public class Chatroom {
    private String Cname;
        private String CID ;
        private String FID ;
        private  String room;
        private String fname;



    public Chatroom (){

    }
    public Chatroom (Chatroom c){
        this.setFID(c.FID);
        this.setCID(c.CID);
        this.setCname(c.Cname);
        this.setroom(c.room);
    this.setFname(c.fname);}

    public Chatroom(String CID, String FID, String room, String cname,String fname) {
        this.CID = CID;
        this.FID = FID;
        this.room = room;
       this.Cname = cname;
       this.fname=fname;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        this.Cname = cname;
    }
        public void setFID(String FID) {
            this.FID = FID;
        }
        public void setCID(String CID) {
            this.CID = CID;
        }
        public  String getroom() { return room;}
        public String getFID() {
            return FID;
        }
        public  String getCID() {
            return CID;
        }


    public void setroom(String room) {
        this.room = room;
    }
}
