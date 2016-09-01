package com.example.lsy_android.mvpmodel.city.bean;

/**
 * Created by jyj-lsy on 8/30/16 in zsl-tech.
 */
public class City {
    public String name;
    public String pinyi;

    public City(String name, String pinyi) {
        super();
        this.name = name;
        this.pinyi = pinyi;
    }

    public City() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyi() {
        return pinyi;
    }

    public void setPinyi(String pinyi) {
        this.pinyi = pinyi;
    }
}
