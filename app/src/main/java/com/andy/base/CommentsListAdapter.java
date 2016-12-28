package com.andy.base;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.base.beans.CommentInfo;

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
