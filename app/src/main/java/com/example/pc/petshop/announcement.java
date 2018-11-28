package com.example.pc.petshop;

public class announcement {
    private String id,email , sms ;
    public void setOb(announcement chat) {
        this.id = chat.id;
        this.email = chat.email;
        this.sms=chat.sms;
    }

    public announcement( ){}
    public announcement(String id , String email , String sms ) {
        this.id=id;
        this.email=email;
        this.sms=sms;

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnn() {
        return sms;
    }

    public void setAnn(String id) {
        this.sms = id;
    }

    public String getName() {
        return email;
    }

    public void setName(String name) {
        this.email = name;
    }
}

/*package com.example.pc.petshop;

class carto {
    private String id,name;


    public carto(String postOwnerID, String postUsername) {
        this.id=postOwnerID;
        this.id=postUsername;
    }
    public String getId() {
        return id;
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
*/