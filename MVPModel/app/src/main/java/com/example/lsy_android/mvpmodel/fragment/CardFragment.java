package com.example.lsy_android.mvpmodel.fragment;

import android.support.v7.widget.CardView;
import android.widget.TextView;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.event.WeatherMessage;
import com.example.lsy_android.mvpmodel.views.CardAdapter;

import org.greenrobot.eventbus.Subscribe;


public class CardFragment extends BaseFragment {

    private CardView mCardView;
    private TextView tvContentGet;

    public CardView getCardView() {
        return mCardView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_adapter;
    }

    @Override
    protected void initView() {
        mCardView = findView(R.id.cardView);
        tvContentGet = findView(R.id.tv_content);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
    }

    @Override
    protected void initData() {

    }

    @Subscribe(sticky = true)
    public void onWeatherMsgEarn(WeatherMessage event) {
        tvContentGet.setText(event.getWeatherInfo());
    }

}
