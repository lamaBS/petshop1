package com.example.pc.petshop;

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
