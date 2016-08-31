package com.example.lsy_android.mvpmodel.presenter;

/**
 * Created by jyj-lsy on 8/30/16 in zsl-tech.
 */
public interface CityPresenter {
    /**
     * 获取城市列表
     */
    void getAllCityList();

    /**
     * 查询城市时获取的城市列表
     * 可能有多个
     * @param name 查询的城市名称
     */
    void getSearchCityList(String name);
}
