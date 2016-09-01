package com.example.lsy_android.mvpmodel.city.presenter;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public interface CityPresenterNormal {
    /**
     * 组合全部城市的列表
     */
    void combineAllCity();

    /**
     * 组合定位获取的城市信息
     */
    void combineLocateCity();

    /**
     * 组合热门的城市信息
     */
    void combineHotCity();

    /**
     * 组合最近访问的城市信息
     */
    void combineRecentCity();

    /**
     * 组合搜索获取的城市信息
     */
    void combineSearchedCity(String cityName);
}
