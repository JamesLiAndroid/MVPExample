package com.example.lsy_android.mvpmodel.view.impl;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.city.adapter.HotCityAdapter;
import com.example.lsy_android.mvpmodel.city.adapter.RecentCityAdapter;
import com.example.lsy_android.mvpmodel.city.adapter.ResultListAdapter;
import com.example.lsy_android.mvpmodel.city.utils.DBUtils;
import com.example.lsy_android.mvpmodel.city.utils.PingYinUtils;
import com.example.lsy_android.mvpmodel.city.views.MyLetterListView;
import com.example.lsy_android.mvpmodel.model.entity.City;
import com.example.lsy_android.mvpmodel.view.CitySelectView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市选择页面
 */
public class CitySelectActivity extends AppCompatActivity
        implements AbsListView.OnScrollListener, View.OnClickListener, CitySelectView {

    private ListAdapter adapter;
    private ResultListAdapter resultListAdapter;
    private ListView personList;
    private ListView resultList;
    private TextView overlay; // 对话框首字母textview
    private MyLetterListView letterListView; // A-Z listview
    private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
    private Handler handler;
    private OverlayThread overlayThread; // 显示首字母对话框
    private ArrayList<City> allCity_lists; // 所有城市列表
    private ArrayList<City> city_lists; // 城市列表
    private ArrayList<City> city_hot; // 热门城市列表
    private ArrayList<City> city_result; // 搜索获取的城市列表
    private ArrayList<String> city_history;
    private EditText sh;
    private TextView tv_noresult;

    // TODO：需要集成百度地图功能才可以实现定位城市
    // private MyLocationListener mMyLocationListener;

    private String currentCity; // 用于保存定位到的城市
    private int locateProcess = 1; // 记录当前定位的状态 正在定位-定位成功-定位失败
    private boolean mReady;

    WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        initView();
    }

    protected void initView() {
        TextView back = (TextView) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
        personList = (ListView) findViewById(R.id.list_view);
        allCity_lists = new ArrayList<City>();
        city_hot = new ArrayList<City>();
        city_result = new ArrayList<City>();
        city_history = new ArrayList<String>();
        resultList = (ListView) findViewById(R.id.search_result);
        sh = (EditText) findViewById(R.id.sh);
        tv_noresult = (TextView) findViewById(R.id.tv_noresult);
        sh.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString() == null || "".equals(s.toString())) {
                    letterListView.setVisibility(View.VISIBLE);
                    personList.setVisibility(View.VISIBLE);
                    resultList.setVisibility(View.GONE);
                    tv_noresult.setVisibility(View.GONE);
                } else {
                    city_result.clear();
                    letterListView.setVisibility(View.GONE);
                    personList.setVisibility(View.GONE);
                    city_result = DBUtils.getInstance().getResultCityList(CitySelectActivity.this, s.toString());
                    if (city_result.size() <= 0) {
                        tv_noresult.setVisibility(View.VISIBLE);
                        resultList.setVisibility(View.GONE);
                    } else {
                        tv_noresult.setVisibility(View.GONE);
                        resultList.setVisibility(View.VISIBLE);
                        resultListAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        letterListView = (MyLetterListView) findViewById(R.id.my_letter_list);
        letterListView.setOnTouchingLetterChangedListener(new LetterListViewListener());
        alphaIndexer = new HashMap<String, Integer>();
        handler = new Handler();
        overlayThread = new OverlayThread();
        //isNeedFresh = true;
        /**
         * list列表
         */
        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 4) {
                    resultCity(allCity_lists.get(position).getName());
                }
            }
        });
        locateProcess = 1;
        personList.setAdapter(adapter);
        personList.setOnScrollListener(this);
        resultListAdapter = new ResultListAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        /**
         * 查询结果处理
         */
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resultCity(city_result.get(position).getName());

            }
        });
        initOverlay();
        cityInit();
        hotCityInit();
        city_history = DBUtils.getInstance().hisCityInit(CitySelectActivity.this);
        setAdapter(allCity_lists, city_hot, city_history);
        // -----------定位
       // lm = new LocationManager(this);
        //lm.registerLocationListener(locationListener, false);
    }

    @Override
    protected void onDestroy() {
       /* if (locationListener != null) {

            lm.unRegisterLocationListener(locationListener);
        }*/
        windowManager.removeView(overlay);
        super.onDestroy();
    }

    private void cityInit() {
        City city = new City("定位", "0"); // 当前定位城市
        allCity_lists.add(city);
        city = new City("最近", "1"); // 最近访问的城市
        allCity_lists.add(city);
        city = new City("热门", "2"); // 热门城市
        allCity_lists.add(city);
        city = new City("全部", "3"); // 全部城市
        allCity_lists.add(city);
        city_lists = DBUtils.getInstance().getCityList(CitySelectActivity.this);
        allCity_lists.addAll(city_lists);
    }

    /**
     * 热门城市
     */
    public void hotCityInit() {
        City city = new City("济南", "2");
        city_hot.add(city);
        city = new City("北京", "2");
        city_hot.add(city);
        city = new City("大连", "2");
        city_hot.add(city);
        city = new City("上海", "2");
        city_hot.add(city);
        city = new City("深圳", "2");
        city_hot.add(city);
        city = new City("淄博", "2");
        city_hot.add(city);
        city = new City("临沂", "2");
        city_hot.add(city);
        city = new City("大庆", "2");
        city_hot.add(city);
        city = new City("青岛", "2");
        city_hot.add(city);
        city = new City("重庆", "2");
        city_hot.add(city);
    }

    private void setAdapter(List<City> list, List<City> hotList, List<String> hisCity) {
        adapter = new ListAdapter(this, list, hotList, hisCity);
        alphaIndexer = adapter.getAlphaIndexerNext();
        personList.setAdapter(adapter);
    }

   // private LocationManager lm;

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showCitiesError() {
        Toast.makeText(CitySelectActivity.this, "读取城市信息失败！", Toast.LENGTH_SHORT).show();
        tv_noresult.setVisibility(View.VISIBLE);
        resultList.setVisibility(View.GONE);
        personList.setVisibility(View.GONE);
    }

    @Override
    public void setCitiesInfo(List<City> cities) {
        cityInit();
        hotCityInit();
        city_history = DBUtils.getInstance().hisCityInit(CitySelectActivity.this);
        setAdapter(allCity_lists, city_hot, city_history);
    }
   /* *//**
     * 实现实位回调监听
     *//*
    private BDLocationListener locationListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (!isNeedFresh) {
                return;
            }
            isNeedFresh = false;
            if (location.getCity() == null) {
                locateProcess = 3; // 定位失败
                personList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            }
            currentCity = location.getCity().substring(0, location.getCity().length() - 1);
            locateProcess = 2; // 定位成功
            personList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            lm.unRegisterLocationListener(locationListener);
        }
    };

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation arg0) {
            if (!isNeedFresh) {
                return;
            }
            isNeedFresh = false;
            if (arg0.getCity() == null) {
                locateProcess = 3; // 定位失败
                personList.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                return;
            }
            currentCity = arg0.getCity().substring(0, arg0.getCity().length() - 1);
            locateProcess = 2; // 定位成功
            personList.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

    }*/

    public class ListAdapter extends BaseAdapter {
        private Context context;
        private LayoutInflater inflater;
        private List<City> list;
        private List<City> hotList;
        private List<String> hisCity;
        final int VIEW_TYPE = 5;

        CityViewHolder holder;
        LocateCityViewHolder locateCityViewHolder;
        HotCityViewHolder hotCityViewHolder;
        RecenetCityViewHolder recenetCityViewHolder;

        private String[] selfSections; // 存放存在的汉语拼音首字母
        private HashMap<String, Integer> alphaIndexerNext = new HashMap<>();

        public ListAdapter(Context context, List<City> list, List<City> hotList, List<String> hisCity) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
            this.context = context;
            this.hotList = hotList;
            this.hisCity = hisCity;
            selfSections = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                // 当前汉语拼音首字母
                String currentStr = PingYinUtils.getAlpha(list.get(i).getPinyi());
                // 上一个汉语拼音首字母，如果不存在为" "
                String previewStr = (i - 1) >= 0 ? PingYinUtils.getAlpha(list.get(i - 1).getPinyi()) : " ";
                if (!previewStr.equals(currentStr)) {
                    String firstLetter = PingYinUtils.getAlpha(list.get(i).getPinyi());
                    alphaIndexerNext.put(firstLetter, i);
                    selfSections[i] = firstLetter;
                }
            }
        }

        public String[] getSelfSections() {
            return selfSections;
        }


        public HashMap<String, Integer> getAlphaIndexerNext() {
            return alphaIndexerNext;
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
            final TextView city;
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
                // TODO:后续需要修改为读取定位之后的本地数据
                locateCityViewHolder.city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 定位成功
                        if (locateProcess == 2) {
                            resultCity(locateCityViewHolder.city.getText().toString());

                         /*   if (locationListener != null) {
                                lm.unRegisterLocationListener(locationListener);
                            }*/

                        } else if (locateProcess == 3) {
                            locateProcess = 1;
                            personList.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                           // isNeedFresh = true;
                            currentCity = "";
                          /*  lm = new LocationManager(CitySelcet.this);
                            lm.registerLocationListener(locationListener, false);*/
                        }
                    }
                });
                if (locateProcess == 1) { // 正在定位
                    locateCityViewHolder.locateHint.setText("正在定位");
                    locateCityViewHolder.city.setVisibility(View.GONE);
                    locateCityViewHolder.pbLocate.setVisibility(View.VISIBLE);
                } else if (locateProcess == 2) { // 定位成功
                    locateCityViewHolder.locateHint.setText("当前定位城市");
                    locateCityViewHolder.city.setVisibility(View.VISIBLE);
                    locateCityViewHolder.city.setText(currentCity);
                    locateCityViewHolder.pbLocate.setVisibility(View.GONE);
                } else if (locateProcess == 3) {
                    locateCityViewHolder.locateHint.setText("未定位到城市,请选择");
                    locateCityViewHolder.city.setVisibility(View.VISIBLE);
                    locateCityViewHolder.city.setText("重新选择");
                    locateCityViewHolder.pbLocate.setVisibility(View.GONE);
                }
            } else if (viewType == 1) { // 最近访问城市
                if (recenetCityViewHolder == null) {
                    recenetCityViewHolder = new RecenetCityViewHolder();
                    convertView = inflater.inflate(R.layout.recent_city, null);
                    recenetCityViewHolder.recentCity = (GridView) convertView.findViewById(R.id.recent_city);
                    convertView.setTag(recenetCityViewHolder);
                } else {
                    recenetCityViewHolder = (RecenetCityViewHolder) convertView.getTag();
                }
                recenetCityViewHolder.recentCity.setAdapter(new RecentCityAdapter(context, this.hisCity));
                recenetCityViewHolder.recentCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getApplicationContext(), city_history.get(position), Toast.LENGTH_SHORT).show();
                        String city = city_history.get(position);
                        resultCity(city);
                    }

                });
            } else if (viewType == 2) { // 热门
                if (hotCityViewHolder == null) {
                    hotCityViewHolder = new HotCityViewHolder();
                    convertView = inflater.inflate(R.layout.recent_city, null);
                    hotCityViewHolder.hotCity = (GridView) convertView.findViewById(R.id.recent_city);
                    convertView.setTag(hotCityViewHolder);
                } else {
                    hotCityViewHolder = (HotCityViewHolder) convertView.getTag();
                }
                hotCityViewHolder.hotCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        resultCity(city_hot.get(position).getName());
                    }
                });
                hotCityViewHolder.hotCity.setAdapter(new HotCityAdapter(context, this.hotList));
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
            }
            return convertView;
        }

        private class LocateCityViewHolder {
            TextView locateHint;
            TextView city;
            ProgressBar pbLocate;
        }

        private class RecenetCityViewHolder {
            GridView recentCity;
        }

        private class HotCityViewHolder {
            GridView hotCity;
        }

        private class CityViewHolder {
            TextView alpha; // 首字母标题
            TextView name; // 城市名字
        }
    }

    /**
     * 返回选中的城市信息
     * @param city
     */
    private void resultCity(String city) {
        Intent in = new Intent();
        in.putExtra("result", city);
        DBUtils.getInstance().insertCity(CitySelectActivity.this, city);
        setResult(RESULT_OK, in);

        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    /**
     * 汉语拼音首字母弹出提示框, 注意windowLeak的问题
     */
    private void initOverlay() {
        mReady = true;
        LayoutInflater inflater = LayoutInflater.from(this);
        overlay = (TextView) inflater.inflate(R.layout.overlay, null);
        overlay.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                PixelFormat.TRANSLUCENT);
        windowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(overlay, lp);
    }

    private boolean isScroll = false;

    private class LetterListViewListener implements MyLetterListView.OnTouchingLetterChangedListener {

        @Override
        public void onTouchingLetterChanged(final String s) {
            isScroll = false;
            if (alphaIndexer.get(s) != null) {
                int position = alphaIndexer.get(s);
                personList.setSelection(position);
                overlay.setText(s);
                overlay.setVisibility(View.VISIBLE);
                handler.removeCallbacks(overlayThread);
                // 延迟一秒后执行，让overlay为不可见
                handler.postDelayed(overlayThread, 1000);
            }
        }
    }

    /**
     * 设置overlay不可见，为Runnable，配合Handler进行隐藏
      */
    private class OverlayThread implements Runnable {
        @Override
        public void run() {
            overlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_TOUCH_SCROLL || scrollState == SCROLL_STATE_FLING) {
            isScroll = true;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (!isScroll) {
            return;
        }

        if (mReady) {
            String text;
            String name = allCity_lists.get(firstVisibleItem).getName();
            String pinyin = allCity_lists.get(firstVisibleItem).getPinyi();
            if (firstVisibleItem < 4) {
                text = name;
            } else {
                text = PingYinUtils.converterToFirstSpell(pinyin).substring(0, 1).toUpperCase();
            }
            overlay.setText(text);
            overlay.setVisibility(View.VISIBLE);
            handler.removeCallbacks(overlayThread);
            // 延迟一秒后执行，让overlay为不可见
            handler.postDelayed(overlayThread, 1000);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                CitySelectActivity.this.finish();
                break;
            default:
                break;
        }
    }

}
