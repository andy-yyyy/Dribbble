package com.andy.dribbble.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andy.dribbble.R;

/**
 * 左边图片右边文字
 * Created by andy on 2017/9/2.
 */

public class IconText extends LinearLayout {

    protected LinearLayout mRootView;
    protected ImageView mIconView;
    protected TextView mTextView;

    protected String mText;
    protected int mIconRes;
    public IconText(Context context) {
        super(context);
        initView();
    }

    public IconText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
        initView();
//        refreshView();
    }

    protected void initView() {
        mRootView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_icon_text, this, true);
        mIconView = (ImageView) findViewById(R.id.icon);
        mTextView = (TextView) findViewById(R.id.text);
    }

    protected void initAttr(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.IconText, 0, 0);
        mText = ta.getString(R.styleable.IconText_text);
        mIconRes = ta.getResourceId(R.styleable.IconText_icon, -1);
        ta.recycle();
    }

    public void setIconRes(int iconRes) {
        this.mIconRes = iconRes;
        refreshView();
    }

    public void setText(String text) {
        this.mText = text;
        refreshView();
    }

    public void refreshView() {
        mTextView.setText(mText);
        if (mIconRes != 0 && mIconRes != -1) {
            mIconView.setImageResource(mIconRes);
        }
    }
}
