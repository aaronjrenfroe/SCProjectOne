package com.aaronr.googlespringone;

/**
 * Created by AaronR on 10/17/17.
 * for ?
 */
public class Greeting {
    private int id;
    private String content;

    public Greeting() {
        this.id = 0;
        this.content = "";
    }

    public String getContent() {
        return content;
    }

    public Greeting(int id, String name){
        this.id = id;
        this.content = name;
    }

    public int getId() {
        return id;
    }

    public void setContent(String c){
        content = c;
    }
}


//https://github.com/aaronjrenfroe/SCProjectOne