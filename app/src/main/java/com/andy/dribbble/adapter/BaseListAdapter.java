package com.andy.dribbble.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andy.dribbble.R;
import com.andy.dribbble.adapter.ExtensibleListAdapter;

import java.util.List;

/**
 * Created by andy on 2016/12/18.
 */
public abstract class BaseListAdapter<D, H extends RecyclerView.ViewHolder> extends ExtensibleListAdapter<D, H> {

    private static final String DEFAULT_NO_DATA_TIPS = "——我也是有底线的——";
    private static final String DEFAULT_LOADING_TIPS = "加载中...";
    private OnItemClickListener mOnItemClickListener;
    private ProgressBar mPb;
    private TextView mTipView;

    public BaseListAdapter(Context context) {
        super(context);
    }

    public BaseListAdapter(Context context, List<D> data) {
        super(context, data);
    }


    @Override
    protected void onBindItemHolder(H itemHolder, int position) {
        final H holder = itemHolder;
        final int pos = position;
        if (mOnItemClickListener != null && itemHolder != null) {
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

    @Override
    protected View onCreateFooter(ViewGroup parent) {
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_load_more, parent, false);
        mPb = (ProgressBar) footer.findViewById(R.id.pb);
        mTipView = (TextView) footer.findViewById(R.id.tv);
        return footer;
    }

    @Override
    public boolean isFooterEnabled() {
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    public void showLoadingView(boolean show) {
        showFooterView(show);
        if (mPb != null) {
            mPb.setVisibility(View.VISIBLE);
        }
        if (mTipView != null) {
            mTipView.setVisibility(View.VISIBLE);
            mTipView.setText(DEFAULT_LOADING_TIPS);
        }
    }

    public void showFooterTips() {
        showFooterTips(DEFAULT_NO_DATA_TIPS);
    }

    public void showFooterTips(String tip) {
        showFooterView(true);
        if (mPb != null) {
            mPb.setVisibility(View.GONE);
        }
        if (mTipView != null) {
            mTipView.setVisibility(View.VISIBLE);
            mTipView.setText(tip);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
}
