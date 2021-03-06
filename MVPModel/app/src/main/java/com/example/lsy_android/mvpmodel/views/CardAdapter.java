package com.example.lsy_android.mvpmodel.views;


import android.support.v7.widget.CardView;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 4;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
