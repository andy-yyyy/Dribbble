package com.andy.base;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/12.
 */
public class ViewPagerIndicator extends LinearLayout {

    protected LinearLayout mRootView;
    protected List<TextView> mItemViewList = new ArrayList<>();
    protected int mCurrentPos;
    protected int mLastPos;

    public ViewPagerIndicator(Context context) {
        super(context);
        initView(context);
    }

    public ViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setSelection(int pos) {
        if (pos < 0 || pos > mItemViewList.size() - 1) {
            return;
        }
        this.mLastPos = mCurrentPos;
        this.mCurrentPos = pos;
        updateSelectedItem();
    }

    protected void updateSelectedItem() {
        mItemViewList.get(mLastPos).setTextColor(Color.BLACK);
        mItemViewList.get(mCurrentPos).setTextColor(Color.WHITE);
    }

    public void addItem(String title) {
        TextView item = new TextView(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.weight = 1;
        item.setLayoutParams(lp);
        item.setGravity(Gravity.CENTER);
        if (!TextUtils.isEmpty(title)) {
            item.setText(title);
        }
        addItemView(item);
    }

    public void addItemView(View view) {
        mRootView.addView(view);
        mItemViewList.add((TextView) view);
    }
    private void initView(Context context) {
        mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_indicator, this, false);
        addView(mRootView);
    }
}
