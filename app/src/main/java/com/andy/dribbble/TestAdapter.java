package com.andy.dribbble;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by andy on 2016/12/15.
 */
public class TestAdapter extends BaseListAdapter<String, TestAdapter.ItemHolder> {

    public TestAdapter(Context context) {
        super(context);
    }

    @Override
    protected ItemHolder onCreateItemHolder(ViewGroup parent) {
        return new ItemHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

//    @Override
//    protected View onCreateHeader(ViewGroup parent) {
//        return LayoutInflater.from(mContext).inflate(R.layout.footer_load_more, parent, false);
//    }
//
    @Override
    protected View onCreateFooter(ViewGroup parent) {
        return LayoutInflater.from(mContext).inflate(R.layout.footer_load_more, parent, false);
    }

    @Override
    public boolean isFooterEnabled() {
        return true;
    }

    @Override
    public boolean isHeaderEnabled() {
        return true;
    }

    @Override
    protected void onBindItemHolder(ItemHolder itemHolder, int position) {
        ItemHolder holder = (ItemHolder) itemHolder;
        Object obj = mData.get(position);
        holder.tv.setText(obj.toString());
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ItemHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }

    class FooterHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ProgressBar pb;
        FooterHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            pb = (ProgressBar) itemView.findViewById(R.id.pb);
        }
    }
}
