/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.andy.dribbble.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/12.
 */
public class BaseTabView<T extends View> extends LinearLayout {

    protected LinearLayout mRootView;
    protected List<T> mItemViewList = new ArrayList<>();
    protected int mCurrentPos;
    protected int mLastPos;

    private OnItemSelectedListener mListener;

    public BaseTabView(Context context) {
        super(context);
        initView(context);
    }

    public BaseTabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void setSelection(int pos) {
        if (pos < 0 || pos > mItemViewList.size() - 1) {
            return;
        }
        this.mLastPos = mCurrentPos;
        this.mCurrentPos = pos;
        T itemView = mItemViewList.get(pos);
        updateSelectedItem(itemView, pos);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener listener) {
        this.mListener = listener;
    }

    protected void updateSelectedItem(T itemView, int pos) {

    }

       public void addItemView(final T view) {
        mRootView.addView(view);
        mItemViewList.add(view);
        if (mListener != null) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onItemSelected(view, mItemViewList.size() - 1);
                }
            });
        }
    }

    private void initView(Context context) {
        mRootView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.layout_base_tab, this, false);
        addView(mRootView);
    }

    interface OnItemSelectedListener {
        void onItemSelected(View itemView, int pos);
    }
}
