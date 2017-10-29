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
    } //商品首字母
    public String getName(){
        return name;
    }  //商品名字
    public String getPrice(){
        return price;
    }  //商品价格
    public String getType(){
        return type;
    }  //商品信息类型
    public String getInformation(){
        return information;
    }  //商品信息
    public int getImageindex(){return imageindex;}  //商品图片
}

