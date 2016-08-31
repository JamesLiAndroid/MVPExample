package com.example.lsy_android.mvpmodel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lsy_android.mvpmodel.R;

import java.util.List;

/**
 * Created by jyj-lsy on 8/31/16 in zsl-tech.
 */
public class RecentCityAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<String> hotCitys;

    private ViewHolder viewHolder;

    public RecentCityAdapter(Context context, List<String> hotCitys) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        this.hotCitys = hotCitys;
    }

    @Override
    public int getCount() {
        return hotCitys.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_city, null);
            viewHolder.tvCity = (TextView) convertView.findViewById(R.id.city);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvCity.setText(hotCitys.get(position));
        return convertView;
    }

    public class ViewHolder {
        TextView tvCity;
    }
}
