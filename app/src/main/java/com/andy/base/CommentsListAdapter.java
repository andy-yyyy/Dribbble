package com.andy.base;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.base.beans.CommentInfo;
import com.andy.base.beans.UserInfo;

/**
 * Created by andy on 2016/12/28.
 */
public class CommentsListAdapter extends BaseListAdapter<CommentInfo, CommentsListAdapter.Holder> {

    private Fragment mFrag;
    public CommentsListAdapter(Fragment frag) {
        super(frag.getContext());
        this.mFrag = frag;
    }

    @Override
    protected Holder onCreateItemHolder(ViewGroup parent) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comments_list, parent, false));
    }

    @Override
    protected void onBindItemHolder(Holder holder, int position) {
        super.onBindItemHolder(holder, position);
        CommentInfo info = mData.get(position);
        UserInfo userInfo = info.getUserInfo();
        if(userInfo != null) {
            holder.userName.setText(userInfo.getUserName());
        }
        holder.content.setText(info.getBody());
        holder.likesCount.setText(getFormatString(R.string.likes_count, info.getLikesCount()));
        holder.time.setText(info.getCreateTime());
    }

    private String getFormatString(int resId, Object obj) {
        return String.format(mFrag.getContext().getResources().getString(resId), obj);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView userName, time, likesCount, content;
        ImageView userAvatar;
        public Holder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time = (TextView) itemView.findViewById(R.id.time);
            likesCount = (TextView) itemView.findViewById(R.id.likes_count);
            content = (TextView) itemView.findViewById(R.id.content);
            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
        }
    }
}
