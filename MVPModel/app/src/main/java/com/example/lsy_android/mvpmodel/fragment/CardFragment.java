package com.example.lsy_android.mvpmodel.fragment;

import android.support.v7.widget.CardView;

import com.example.lsy_android.mvpmodel.R;
import com.example.lsy_android.mvpmodel.views.CardAdapter;


/**
 * 天气Fragment编写模板
 */
public class CardFragment extends BaseFragment {

    private CardView mCardView;
   // private TextView tvContentGet;

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
        //tvContentGet = findView(R.id.tv_content);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
    }

    @Override
    protected void initData() {

    }
}
