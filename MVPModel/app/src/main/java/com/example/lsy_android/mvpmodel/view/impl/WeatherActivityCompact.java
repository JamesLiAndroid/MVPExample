package com.example.lsy_android.mvpmodel.view.impl;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.adapter.CardFragmentPagerAdapter;
import com.example.lsy_android.mvpmodel.views.ShadowTransformer;

public class WeatherActivityCompact  extends AppCompatActivity implements View.OnClickListener,
        CompoundButton.OnCheckedChangeListener {

    private Button mButton;
    private ViewPager mViewPager;
    
    private CardFragmentPagerAdapter mFragmentCardAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;

    private boolean mShowingFragments = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mButton = (Button) findViewById(R.id.cardTypeBtn);
        ((CheckBox) findViewById(R.id.checkBox)).setOnCheckedChangeListener(this);
        mButton.setOnClickListener(this);

        mFragmentCardAdapter = new CardFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(2, this));

        mFragmentCardShadowTransformer = new ShadowTransformer(mViewPager, mFragmentCardAdapter);

        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
        mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onClick(View view) {
        mButton.setText("Views");
        mViewPager.setAdapter(mFragmentCardAdapter);
        mViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        mFragmentCardShadowTransformer.enableScaling(b);
    }
}
