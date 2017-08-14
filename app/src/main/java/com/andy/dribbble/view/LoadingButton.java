package com.andy.dribbble.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andy.dribbble.R;

/**
 * Created by lixn on 2017/1/22.
 */

public class LoadingButton extends RelativeLayout {

    private TextView tv;
    private ProgressBar pb;

    public LoadingButton(Context context) {
        super(context);
        initView(context);
    }

    public LoadingButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_loading_button, this, true);
        tv = (TextView) findViewById(R.id.tv);
        pb = (ProgressBar) findViewById(R.id.pb);
        setBackground(R.drawable.bg_shape_unchecked);
    }

    public void showLoading(boolean show) {
        if (show) {
            pb.setVisibility(VISIBLE);
            tv.setVisibility(INVISIBLE);
        } else {
            pb.setVisibility(INVISIBLE);
            tv.setVisibility(VISIBLE);
        }
    }

    public void setText(String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        tv.setText(text);
    }

    public void setBackground(int resId) {
        setBackgroundResource(resId);
    }

    public void toggle(boolean toggleOn, String title) {
        setText(title);
        if (toggleOn) {
            tv.setTextColor(getResources().getColor(R.color.white));
            setBackground(R.drawable.bg_shape_checked);
        } else {
            tv.setTextColor(getResources().getColor(R.color.deep_gray));
            setBackground(R.drawable.bg_shape_unchecked);
        }

    }


}
