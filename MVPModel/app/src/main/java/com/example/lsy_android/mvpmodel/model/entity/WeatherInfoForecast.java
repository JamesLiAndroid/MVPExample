
package com.example.lsy_android.mvpmodel.model.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherInfoForecast {

    @SerializedName("hightemp")
    @Expose
    private String hightemp;
    @SerializedName("fengxiang")
    @Expose
    private String fengxiang;
    @SerializedName("lowtemp")
    @Expose
    private String lowtemp;
    @SerializedName("fengli")
    @Expose
    private String fengli;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("week")
    @Expose
    private String week;

    /**
     * 
     * @return
     *     The hightemp
     */
    public String getHightemp() {
        return hightemp;
    }

    /**
     * 
     * @param hightemp
     *     The hightemp
     */
    public void setHightemp(String hightemp) {
        this.hightemp = hightemp;
    }

    /**
     * 
     * @return
     *     The fengxiang
     */
    public String getFengxiang() {
        return fengxiang;
    }

    /**
     * 
     * @param fengxiang
     *     The fengxiang
     */
    public void setFengxiang(String fengxiang) {
        this.fengxiang = fengxiang;
    }

    /**
     * 
     * @return
     *     The lowtemp
     */
    public String getLowtemp() {
        return lowtemp;
    }

    /**
     * 
     * @param lowtemp
     *     The lowtemp
     */
    public void setLowtemp(String lowtemp) {
        this.lowtemp = lowtemp;
    }

    /**
     * 
     * @return
     *     The fengli
     */
    public String getFengli() {
        return fengli;
    }

    /**
     * 
     * @param fengli
     *     The fengli
     */
    public void setFengli(String fengli) {
        this.fengli = fengli;
    }

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The week
     */
    public String getWeek() {
        return week;
    }

    /**
     * 
     * @param week
     *     The week
     */
    public void setWeek(String week) {
        this.week = week;
    }

}
