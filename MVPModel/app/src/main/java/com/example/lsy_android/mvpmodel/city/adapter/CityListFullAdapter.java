package com.example.lsy_android.mvpmodel.city.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.city.bean.City;
import com.example.lsy_android.mvpmodel.city.utils.DBUtils;
import com.example.lsy_android.mvpmodel.city.utils.PingYinUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by jyj-lsy on 9/1/16 in zsl-tech.
 */
public class CityListFullAdapter extends BaseAdapter {

    Activity activity;
    private LayoutInflater inflater;
    private List<City> list = new ArrayList<>();
    private List<City> hotList;
    private List<City> hisCity;

    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置

    private City currentCity; // 用于保存定位到的城市
    final int VIEW_TYPE = 5;

    CityViewHolder holder;
    LocateCityViewHolder locateCityViewHolder;
    HotCityViewHolder hotCityViewHolder;
    RecenetCityViewHolder recenetCityViewHolder;

    private String[] selfSections; // 存放存在的汉语拼音首字母

    public CityListFullAdapter(Activity activity) {
        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
    }

    public void addAlphaIndexer(HashMap<String, Integer> alphaIndexer) {
        this.alphaIndexer = alphaIndexer;
        //notifyDataSetChanged();
    }

    public void addAllList(ArrayList<City> allCityList) {
        this.list = allCityList;
        //notifyDataSetChanged();
    }

    public void addRecentCityList(ArrayList<City> recentCityList) {
        this.hisCity = recentCityList;
        //notifyDataSetChanged();
    }

    public void addHotCityList(ArrayList<City> hotCityList) {
        this.hotList = hotCityList;
       // notifyDataSetChanged();
    }

    public void addLocationCity(City city) {
        this.currentCity = city;
        //notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE;
    }

    @Override
    public int getItemViewType(int position) {
        return position < 4 ? position : 4;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int viewType = getItemViewType(position);
        if (viewType == 0) { // 定位
            if (locateCityViewHolder == null) {
                locateCityViewHolder = new LocateCityViewHolder();
                convertView = inflater.inflate(R.layout.frist_list_item, null);
                locateCityViewHolder.locateHint = (TextView) convertView.findViewById(R.id.locateHint);
                locateCityViewHolder.city = (TextView) convertView.findViewById(R.id.lng_city);
                locateCityViewHolder.pbLocate = (ProgressBar) convertView.findViewById(R.id.pbLocate);
                convertView.setTag(locateCityViewHolder);
            } else {
                locateCityViewHolder = (LocateCityViewHolder) convertView.getTag();
            }
            locateCityViewHolder.locateHint.setText("当前定位城市");
            locateCityViewHolder.city.setVisibility(View.VISIBLE);
            locateCityViewHolder.city.setText(currentCity.getName());
            locateCityViewHolder.pbLocate.setVisibility(View.GONE);
            locateCityViewHolder.city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    resultCity(currentCity.getName());
                }
            });
        } else if (viewType == 1) { // 最近访问城市
            if (recenetCityViewHolder == null) {
                recenetCityViewHolder = new RecenetCityViewHolder();
                convertView = inflater.inflate(R.layout.recent_city, null);
                recenetCityViewHolder.recentCity = (GridView) convertView.findViewById(R.id.recent_city);
                recenetCityViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.recentHint);
                convertView.setTag(recenetCityViewHolder);
            } else {
                recenetCityViewHolder = (RecenetCityViewHolder) convertView.getTag();
            }
            recenetCityViewHolder.tvTitle.setText("最近访问");
            recenetCityViewHolder.recentCity.setAdapter(new RecentCityAdapter(activity, this.hisCity));
            recenetCityViewHolder.recentCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(activity, hisCity.get(position).getName(), Toast.LENGTH_SHORT).show();
                    String city = hisCity.get(position).getName();
                    resultCity(city);
                }

            });
        } else if (viewType == 2) { // 热门
            if (hotCityViewHolder == null) {
                hotCityViewHolder = new HotCityViewHolder();
                convertView = inflater.inflate(R.layout.recent_city, null);
                hotCityViewHolder.hotCity = (GridView) convertView.findViewById(R.id.recent_city);
                hotCityViewHolder.tvTitle = (TextView) convertView.findViewById(R.id.recentHint);
                convertView.setTag(hotCityViewHolder);
            } else {
                hotCityViewHolder = (HotCityViewHolder) convertView.getTag();
            }
            hotCityViewHolder.tvTitle.setText("热门城市");
            hotCityViewHolder.hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    resultCity(hotList.get(position).getName());
                }
            });
            hotCityViewHolder.hotCity.setAdapter(new HotCityAdapter(activity, hotList));
        } else if (viewType == 3) { // 全部城市的标签
            convertView = inflater.inflate(R.layout.total_item, null);
        } else { // 全部城市
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, null);
                holder = new CityViewHolder();
                holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (CityViewHolder) convertView.getTag();
            }
            if (position >= 1) {
                holder.name.setText(list.get(position).getName());
                String currentStr = PingYinUtils.getAlpha(list.get(position).getPinyi());
                String previewStr = (position - 1) >= 0 ? PingYinUtils.getAlpha(list.get(position - 1).getPinyi()) : " ";
                if (!previewStr.equals(currentStr)) {
                    holder.alpha.setVisibility(View.VISIBLE);
                    holder.alpha.setText(currentStr);
                } else {
                    holder.alpha.setVisibility(View.GONE);
                }
            }
            // else {
            //    Log.e("TAG", "position == " + position);
           // }
        }
        return convertView;
    }

    private class LocateCityViewHolder {
        TextView locateHint;
        TextView city;
        ProgressBar pbLocate;
    }

    private class RecenetCityViewHolder {
        TextView tvTitle;
        GridView recentCity;
    }

    private class HotCityViewHolder {
        TextView tvTitle;
        GridView hotCity;
    }

    private class CityViewHolder {
        TextView alpha; // 首字母标题
        TextView name; // 城市名字
    }

    /**
     * 返回选中的城市信息
     *
     * @param city
     */
    private void resultCity(String city) {
        Intent in = new Intent();
        in.putExtra("result", city);
        DBUtils.getInstance().insertCity(activity, city);
        activity.setResult(Activity.RESULT_OK, in);
        activity.finish();
    }
}
