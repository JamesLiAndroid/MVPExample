package com.example.lsy_android.mvpmodel.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lsy_android.mvpmodel.R;

/**
 * Created by lsy-android on 8/27/16 in zsl-tech.
 */
public class ListItemAdapter extends BaseAdapter {
    private String[] values;
    private String[] titles;
    private Context context;

    ViewHolder holder;

    public ListItemAdapter(Context context, String[] values) {
        this.context = context;
        this.values = values;
        titles = new String[] {
                "地点", "发布日期", "当前温度", "预测日期", "最高温度", "最低温度", "风力", "风向", "大气状况"
        };
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public Object getItem(int i) {
        return titles[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_layout, null);
            holder.title = (TextView) view.findViewById(R.id.title);
            holder.value = (TextView) view.findViewById(R.id.value);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.title.setText(titles[i]);
        holder.value.setText(values[i]);
        return view;
    }

    static class ViewHolder {
        TextView title;
        TextView value;
    }
}
