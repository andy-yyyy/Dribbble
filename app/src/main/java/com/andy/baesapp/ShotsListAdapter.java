package com.andy.baesapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andy.baesapp.beans.ShotInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListAdapter extends RecyclerView.Adapter<ShotsListAdapter.Holder> {

    private List<ShotInfo> mData = new ArrayList<>();

    public void updateData(List<ShotInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shots_list, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ShotInfo info = mData.get(position);
        holder.tv.setText(info.getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView tv;
        public Holder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
        }
    }
}
