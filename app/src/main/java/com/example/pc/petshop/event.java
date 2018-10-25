package com.example.pc.petshop;

public class event {

    private String eventName;
    private String adress;
    private String description;




    public event() {}

    public event(event p) {

        this.adress=p.getAdress();
        this.eventName=p.getEventName();
        this.description=p.getDescription();



    }
    public event(String eventName, String adress, String description) {
        this.eventName = eventName;
        this.adress = adress;
        this.description = description;

    }
    public String getAdress() {
        return adress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

}
