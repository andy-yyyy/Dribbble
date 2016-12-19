package com.andy.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by andy on 2016/12/18.
 */
public abstract class BaseListAdapter<D, H extends RecyclerView.ViewHolder> extends ExtensibleListAdapter<D, H> {

    private OnItemClickListener mOnItemClickListener;

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
        return LayoutInflater.from(mContext).inflate(R.layout.footer_load_more, parent, false);
    }

    @Override
    public boolean isFooterEnabled() {
        return true;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        this.mOnItemClickListener = itemClickListener;
    }

    interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
}
