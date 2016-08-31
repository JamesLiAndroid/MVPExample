package com.example.lsy_android.mvpmodel.city.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.city.bean.SimpleCity;

import java.util.List;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public class CityAdapter extends BaseAdapter {
    private Context context;
    private List<SimpleCity> cities;

    ViewHolder holder;

    public CityAdapter(Context context, List<SimpleCity> cities) {
        this.context = context;
        this.cities = cities;
    }

    @Override
    public int getCount() {
        return cities.size();
    }

    @Override
    public Object getItem(int position) {
        return cities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_simple_city, null);
            holder.tvItemCity = (TextView) convertView.findViewById(R.id.item_city);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvItemCity.setText(cities.get(position).getCityName());
        return convertView;
    }

    static class ViewHolder {
        TextView tvItemCity;
    }
}
