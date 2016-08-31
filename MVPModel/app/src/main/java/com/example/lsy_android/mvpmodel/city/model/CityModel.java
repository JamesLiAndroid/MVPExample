package com.example.lsy_android.mvpmodel.city.model;


import com.example.lsy_android.mvpmodel.city.bean.SimpleCity;

import java.util.List;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public interface CityModel {
    /**
     * 获取全部城市
     */
    List<SimpleCity> getAllCities();

    /**
     * 搜索城市并返回结果,结果可能是具体的一个城市，
     * 也可能是模糊查询的列表
     * @param name
     */
    List<SimpleCity> getSearchedCities(String name);
}
