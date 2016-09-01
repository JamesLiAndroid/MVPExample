package com.example.lsy_android.mvpmodel.city.presenter;

import com.example.lsy_android.mvpmodel.city.bean.City;
import com.example.lsy_android.mvpmodel.city.model.CityModelNormal;
import com.example.lsy_android.mvpmodel.city.model.CityModelNormalImpl;
import com.example.lsy_android.mvpmodel.city.view.CityViewNormal;

import java.util.ArrayList;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public class CityPresenterNormalImpl implements CityPresenterNormal {

    private CityViewNormal cityView;
    private CityModelNormal cityModel;

    public CityPresenterNormalImpl(CityViewNormal cityView) {
        this.cityView = cityView;
        this.cityModel = new CityModelNormalImpl();
    }

    @Override
    public void combineAllCity() {
        ArrayList<City> allCityList = cityModel.getAllCities();
        cityView.setAllCityList(allCityList);
    }

    @Override
    public void combineLocateCity() {
        City city = cityModel.getLocationCity();
        cityView.setLocateCity(city);
    }

    @Override
    public void combineHotCity() {
        ArrayList<City> allCityList = cityModel.getHotCityList();
        cityView.setHotCityList(allCityList);
    }

    @Override
    public void combineRecentCity() {
        ArrayList<City> allCityList = cityModel.getRecentCityList();
        cityView.setRecentCityList(allCityList);
    }

    @Override
    public void combineSearchedCity(String cityName) {
        ArrayList<City> allCityList = cityModel.getSearchedCities(cityName);
        cityView.setSearchedCityList(allCityList);
    }
}
