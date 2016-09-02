package com.example.lsy_android.mvpmodel.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.adapter.CardFragmentPagerAdapter;
import com.example.lsy_android.mvpmodel.event.WeatherMessage;
import com.example.lsy_android.mvpmodel.views.ShadowTransformer;

import org.greenrobot.eventbus.EventBus;

public class WeatherActivityCompact  extends AppCompatActivity{

    private ViewPager mViewPager;

    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);

        //EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().postSticky(new WeatherMessage("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }
}

