package com.example.lsy_android.mvpmodel.city.view;


import com.example.lsy_android.mvpmodel.city.bean.SimpleCity;

import java.util.List;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public interface CityView {
    /**
     * 设置读取的进度条显示
     */
    void setProgressDialog();

    /**
     * 移除进度条显示
     */
    void moveProgressDialog();

    /**
     * 读取城市信息失败
     */
    void readCitiesError();

    /**
     * 显示已经获取的城市信息
     * @param cities
     */
    void setAllCities(List<SimpleCity> cities);
}
