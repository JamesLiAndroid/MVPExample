package com.example.lsy_android.mvpmodel.city.presenter;


import com.example.lsy_android.mvpmodel.city.bean.SimpleCity;
import com.example.lsy_android.mvpmodel.city.model.CityModel;
import com.example.lsy_android.mvpmodel.city.model.CityModelImpl;
import com.example.lsy_android.mvpmodel.city.view.CityView;

import java.util.List;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public class CityPresenterImpl implements CityPresenter{
    private CityView cityView;
    private CityModel cityModel;

    public CityPresenterImpl(CityView cityView) {
        this.cityView = cityView;
        this.cityModel = new CityModelImpl();
    }

    @Override
    public void validateCityData() {
        List<SimpleCity> cities = cityModel.getAllCities();
        cityView.setAllCities(cities);
    }

    @Override
    public void searchCityData(String name) {
        if (name == null || "".equals(name)) {
            cityView.readCitiesError();
            return;
        }
        List<SimpleCity> cities =  cityModel.getSearchedCities(name);
        cityView.setAllCities(cities);
    }
}
