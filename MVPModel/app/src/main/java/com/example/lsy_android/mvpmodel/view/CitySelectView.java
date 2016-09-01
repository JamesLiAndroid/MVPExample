package com.example.lsy_android.mvpmodel.view;

import com.example.lsy_android.mvpmodel.city.bean.City;

import java.util.List;

/**
 * Created by jyj-lsy on 8/30/16 in zsl-tech.
 */
public interface CitySelectView {
    /**
     * 显示进度条
     */
    void showLoading();

    /**
     * 隐藏进度条
     */
    void dismissLoading();

    /**
     * 显示更新失败的信息
     */
    void showCitiesError();

    /**
     * 显示城市列表信息
     * @param cities
     */
    void setCitiesInfo(List<City> cities);
}
