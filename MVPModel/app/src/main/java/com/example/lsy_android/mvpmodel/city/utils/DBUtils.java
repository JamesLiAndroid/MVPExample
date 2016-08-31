package com.example.lsy_android.mvpmodel.city.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.lsy_android.mvpmodel.model.entity.City;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by lsy-android on 8/31/16 in zsl-tech.
 */
public class DBUtils {

    private static final DBUtils INSTANCE = new DBUtils();

    private DBUtils(){

    }

    public static DBUtils getInstance() {
        return INSTANCE;
    }

    /**
     * 获取所有的城市列表， 并排序
     * @param context
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList<City> getCityList(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        ArrayList<City> list = new ArrayList<City>();
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery("select * from city", null);
            City city;
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                list.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(list, comparator);
        return list;
    }

    /**
     * 获取城市查询的结果，并排序
     * @param context
     * @param keyword
     */
    @SuppressWarnings("unchecked")
    public ArrayList<City> getResultCityList(Context context, String keyword) {
        ArrayList<City> cityList = new ArrayList<>();
        DBHelper dbHelper = new DBHelper(context);
        try {
            dbHelper.createDataBase();
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor cursor = db.rawQuery(
                    "select * from city where name like \"%" + keyword + "%\" or pinyin like \"%" + keyword + "%\"",
                    null);
            City city;
            Log.e("info", "length = " + cursor.getCount());
            while (cursor.moveToNext()) {
                city = new City(cursor.getString(1), cursor.getString(2));
                cityList.add(city);
            }
            cursor.close();
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(cityList, comparator);
        return cityList;
    }

    /**
     * a-z排序
     */
    @SuppressWarnings("rawtypes")
    Comparator comparator = new Comparator<City>() {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getPinyi().substring(0, 1);
            String b = rhs.getPinyi().substring(0, 1);
            int flag = a.compareTo(b);
            if (flag == 0) {
                return a.compareTo(b);
            } else {
                return flag;
            }
        }
    };

    /**
     * 向最近访问的城市的数据库中插入数据
     * @param context
     * @param name
     */
    public void insertCity(Context context, String name) {
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recentcity where name = '" + name + "'", null);
        if (cursor.getCount() > 0) { //
            db.delete("recentcity", "name = ?", new String[] { name });
        }
        db.execSQL("insert into recentcity(name, date) values('" + name + "', " + System.currentTimeMillis() + ")");
        db.close();
    }

    /**
     *
     * @Title: hisCityInit
     * @Description: 最近访问的城市 @param 设定文件 @return void
     *         返回类型 @throws
     */
    public ArrayList<String> hisCityInit(Context context) {
        ArrayList<String> recentCityList = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from recentcity order by date desc limit 0, 3", null);
        while (cursor.moveToNext()) {
            recentCityList.add(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return recentCityList;
    }
}
