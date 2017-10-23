package com.gy.linjliang.shoppingapp;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/10/23.
 */

public class Info implements Serializable {
    private String name;
    private String price;
    private String type;
    private String information;
    private int imageindex;

    public Info(String name, String price, String type, String information,int index) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.information = information;
        this.imageindex = index;
    }

    public String getcycle() {
        String cycle = "";
        return (cycle+name.charAt(0));
    }
    public String getName(){
        return name;
    }
    public String getPrice(){
        return price;
    }
    public String getType(){
        return type;
    }
    public String getInformation(){
        return information;
    }
    public int getImageindex(){return imageindex;}
}

