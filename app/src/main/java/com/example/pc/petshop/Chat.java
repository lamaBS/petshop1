package com.example.pc.petshop;

/**
 * Created by wafa0 on 20/03/18.
 */

public class Chat {

    private  String msg;
    private String user;
    private String name ;
private  String date;
    public Chat(){
    }

    public Chat(Chat chat) {
        this.msg = chat.msg;
        this.user = chat.user;
        this.date=chat.date;
        this.name=chat.name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Chat(String msg, String user, String name, String date) {
        this.msg = msg;
        this.user = user;
        this.name=name;
        this.date=date;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUser() {
        return user;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
