
package com.example.lsy_android.mvpmodel.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("resultCode")
    @Expose
    private String resultCode;
    @SerializedName("resultDesc")
    @Expose
    private String resultDesc;
    @SerializedName("info")
    @Expose
    private WeatherInfo info;

    /**
     * 
     * @return
     *     The resultCode
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 
     * @param resultCode
     *     The resultCode
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 
     * @return
     *     The resultDesc
     */
    public String getResultDesc() {
        return resultDesc;
    }

    /**
     * 
     * @param resultDesc
     *     The resultDesc
     */
    public void setResultDesc(String resultDesc) {
        this.resultDesc = resultDesc;
    }

    /**
     * 
     * @return
     *     The info
     */
    public WeatherInfo getInfo() {
        return info;
    }

    /**
     * 
     * @param info
     *     The info
     */
    public void setInfo(WeatherInfo info) {
        this.info = info;
    }

}
