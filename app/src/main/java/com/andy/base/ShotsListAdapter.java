package com.andy.base;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.base.beans.ShotInfo;
import com.andy.base.commom_utils.ScreenUtil;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListAdapter extends RecyclerView.Adapter<ShotsListAdapter.Holder> {

    private List<ShotInfo> mData = new ArrayList<>();
    private Fragment mFrag;
    private OnItemClickListener mListener;

    public ShotsListAdapter(Fragment frag) {
        this.mFrag = frag;
    }

    public void updateData(List<ShotInfo> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shots_list, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ShotInfo info = mData.get(position);
        holder.updateTime.setText(info.getUpdateTime());
        holder.likesCount.setText(getFormatString(R.string.likes_count, info.getLikesCount()));
        holder.commentsCount.setText(getFormatString(R.string.comments_count, info.getCommentsCount()));
        holder.viewsCount.setText(getFormatString(R.string.views_count, info.getViewsCount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(v);
                }
            }
        });

        // 设置最后一个item底部的margin
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.itemView.getLayoutParams();
        int bottomMargin = lp.bottomMargin;
        if (position == getItemCount() - 1) {
            bottomMargin = ScreenUtil.dip2px(mFrag.getContext(), 10);
        }
        lp.bottomMargin = bottomMargin;
        holder.itemView.setLayoutParams(lp);

        // 加载图片
        Glide.with(mFrag).load(info.getImages().getNormal()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private String getFormatString(int resId, Object obj) {
        return String.format(mFrag.getContext().getResources().getString(resId), obj);
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView updateTime, likesCount, commentsCount, viewsCount;
        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            updateTime = (TextView) itemView.findViewById(R.id.update_time);
            likesCount = (TextView) itemView.findViewById(R.id.likes_count);
            commentsCount = (TextView) itemView.findViewById(R.id.comments_count);
            viewsCount = (TextView) itemView.findViewById(R.id.view_count);
        }
    }

    interface OnItemClickListener {
        void onItemClick(View v);
    }
}
