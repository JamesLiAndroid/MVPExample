package com.example.lsy_android.mvpmodel.city.view;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.city.adapter.CityListFullAdapter;
import com.example.lsy_android.mvpmodel.city.adapter.ResultListAdapter;
import com.example.lsy_android.mvpmodel.city.bean.City;
import com.example.lsy_android.mvpmodel.city.presenter.CityPresenterNormal;
import com.example.lsy_android.mvpmodel.city.presenter.CityPresenterNormalImpl;
import com.example.lsy_android.mvpmodel.city.utils.DBUtils;
import com.example.lsy_android.mvpmodel.city.utils.PingYinUtils;
import com.example.lsy_android.mvpmodel.city.views.MyLetterListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 城市选择页面
 */
public class CitySelectActivity extends AppCompatActivity
        implements AbsListView.OnScrollListener, View.OnClickListener, CityViewNormal {

    private CityPresenterNormal cityPresenter;

    CityListFullAdapter listFullAdapter;
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
    private ArrayList<City> city_result; // 搜索获取的城市列表

    private EditText sh;
    private TextView tv_noresult;

    private boolean mReady;

    WindowManager windowManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        initView();

        cityPresenter = new CityPresenterNormalImpl(this);

        loadCityData();
    }

    protected void initView() {
        allCity_lists = new ArrayList<City>();
        city_result = new ArrayList<City>();
        alphaIndexer = new HashMap<String, Integer>();

        listFullAdapter = new CityListFullAdapter(CitySelectActivity.this);

        TextView back = (TextView) findViewById(R.id.btn_back);
        back.setOnClickListener(this);
        personList = (ListView) findViewById(R.id.list_view);
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
                    cityPresenter.combineSearchedCity(s.toString());
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
        handler = new Handler();
        overlayThread = new OverlayThread();

        personList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 4) {
                    resultCity(allCity_lists.get(position + 4).getName());
                }
            }
        });

        personList.setAdapter(listFullAdapter);
        personList.setOnScrollListener(this);

        resultListAdapter = new ResultListAdapter(this, city_result);
        resultList.setAdapter(resultListAdapter);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                resultCity(city_result.get(position).getName());
            }
        });

        initOverlay();
        cityInit();
    }

    private void loadCityData() {
        cityPresenter.combineLocateCity();
        cityPresenter.combineRecentCity();
        cityPresenter.combineHotCity();
        cityPresenter.combineAllCity();
    }

    @Override
    protected void onDestroy() {
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
        //city_lists = DBUtils.getInstance().getCityList(CitySelectActivity.this);
        //allCity_lists.addAll(city_lists);
    }

    @Override
    public void showProgressBar() {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void setAllCityList(ArrayList<City> allCityList) {
        this.city_lists = allCityList;
        allCity_lists.addAll(city_lists);
        alphaIndexer = initFirstLetter(allCityList);
        listFullAdapter.addAllList(allCity_lists);
        listFullAdapter.addAlphaIndexer(alphaIndexer);
        personList.setAdapter(listFullAdapter);
    }

    @Override
    public void setLocateCity(City city) {
        listFullAdapter.addLocationCity(city);
    }

    @Override
    public void setRecentCityList(ArrayList<City> recentCityList) {
        listFullAdapter.addRecentCityList(recentCityList);
    }

    @Override
    public void setSearchedCityList(ArrayList<City> searchedCityList) {
        this.city_result = searchedCityList;
        resultListAdapter.notifyDataSetChanged();
        letterListView.setVisibility(View.GONE);
        personList.setVisibility(View.GONE);
        if (city_result.size() <= 0) {
            tv_noresult.setVisibility(View.VISIBLE);
            resultList.setVisibility(View.GONE);
        } else {
            tv_noresult.setVisibility(View.GONE);
            resultList.setVisibility(View.VISIBLE);
            resultListAdapter.addAll(city_result);
        }
    }

    @Override
    public void setHotCityList(ArrayList<City> hotCityList) {
        listFullAdapter.addHotCityList(hotCityList);
    }

    /**
     * 返回选中的城市信息
     *
     * @param city
     */
    private void resultCity(String city) {
        Intent in = new Intent();
        in.putExtra("result", city);
        DBUtils.getInstance().insertCity(CitySelectActivity.this, city);
        setResult(RESULT_OK, in);

        finish();
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

    /**
     * 初始化首字母列表
     * @param cityList
     * @return
     */
    private HashMap<String, Integer> initFirstLetter(List<City> cityList) {
        HashMap<String, Integer> alphaIndex = new HashMap<>();
        for (int i = 0; i < cityList.size(); i++) {
            // 当前汉语拼音首字母
            String currentStr = PingYinUtils.getAlpha(cityList.get(i).getPinyi());
            // 上一个汉语拼音首字母，如果不存在为" "
            String previewStr = (i - 1) >= 0 ? PingYinUtils.getAlpha(cityList.get(i - 1).getPinyi()) : " ";
            if (!previewStr.equals(currentStr)) {
                String firstLetter = PingYinUtils.getAlpha(cityList.get(i).getPinyi());
                alphaIndex.put(firstLetter, i);
            }
        }
        return alphaIndex;
    }

}
