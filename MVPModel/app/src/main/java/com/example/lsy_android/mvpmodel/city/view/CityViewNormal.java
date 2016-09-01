package com.example.lsy_android.mvpmodel.city.view;

import com.example.lsy_android.mvpmodel.city.bean.City;

import java.util.ArrayList;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public interface CityViewNormal {
    void showProgressBar();
    void hideProgressBar();

    /**
     * 设置显示所有的城市数据
     * @param allCityList
     */
    void setAllCityList(ArrayList<City> allCityList);

    /**
     * 设置定位的城市显示
     * @param city
     */
    void setLocateCity(City city);

    /**
     * 设置显示最近访问的城市的数据
     * @param recentCityList
     */
    void setRecentCityList(ArrayList<City> recentCityList);

    /**
     * 设置显示搜索后的城市数据
     * @param searchedCityList
     */
    void setSearchedCityList(ArrayList<City> searchedCityList);

    /**
     * 设置显示热门城市列表
     * @param hotCityList
     */
    void setHotCityList(ArrayList<City> hotCityList);
}
