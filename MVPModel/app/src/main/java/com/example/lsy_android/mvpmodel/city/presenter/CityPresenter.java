package com.example.lsy_android.mvpmodel.city.presenter;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public interface CityPresenter {
    /**
     * 刷新所有的城市数据
     */
    void validateCityData();

    /**
     * 刷新搜索的城市数据
     * @param name
     */
    void searchCityData(String name);
}
