package com.example.lsy_android.mvpmodel.network;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lsy-android on 9/3/16 in zsl-tech.
 */
public class BodyString implements Serializable {
    private static final long serialVersionUID = 12344L; //一会就说这个是做什么的

    private String accessId;
    private List<String> citynames;

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public List<String> getCitynames() {
        return citynames;
    }

    public void setCitynames(List<String> citynames) {
        this.citynames = citynames;
    }

    @Override
    public String toString() {
        return "BodyString{" +
                "accessId='" + accessId + '\'' +
                ", citynames=" + citynames +
                '}';
    }
}