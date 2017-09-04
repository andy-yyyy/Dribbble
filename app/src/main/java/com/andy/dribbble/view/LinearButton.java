/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.dribbble.R;

/**
 * Created by andy on 2017/1/7.
 */
public class LinearButton extends LinearLayout {

    private TextView mTitle, mSubTitle;

    public LinearButton(Context context) {
        super(context);
        initView(context);
    }

    public LinearButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void update(String title, String subTitle) {
        this.mTitle.setText(title);
        this.mSubTitle.setText(subTitle);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_linear_button, this, true);
        mTitle = (TextView) findViewById(R.id.title);
        mSubTitle = (TextView) findViewById(R.id.sub_title);
    }
}
