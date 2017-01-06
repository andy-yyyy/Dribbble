/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixn on 2016/12/29.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.Holder> {

    private List<String> mData = new ArrayList<>();
    private Context mContext;

    public MyAdapter(Context context) {
        this.mContext = context;
    }

    public void updateData(List<String> data) {
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.test_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        String title = mData.get(position);
        holder.title.setText(title);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView title;
        public Holder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
