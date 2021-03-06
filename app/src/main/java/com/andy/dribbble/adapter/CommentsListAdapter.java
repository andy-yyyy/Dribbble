package com.andy.dribbble.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.dribbble.R;
import com.andy.dribbble.beans.CommentInfo;
import com.andy.dribbble.beans.UserInfo;
import com.andy.dribbble.common_utils.DateTimeUtil;
import com.andy.dribbble.view.IconText;
import com.bumptech.glide.Glide;

/**
 * Created by andy on 2016/12/28.
 */
public class CommentsListAdapter extends BaseListAdapter<CommentInfo, CommentsListAdapter.Holder> {

    private Fragment mFrag;
    public CommentsListAdapter(Fragment frag) {
        super(frag.getContext());
        this.mFrag = frag;
    }

    public CommentsListAdapter(Context context) {
        super(context);
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
            holder.userName.setText(userInfo.getName());
            Glide.with(mContext).load(userInfo.getAvatarUrl()).into(holder.userAvatar);
        }
        if (!TextUtils.isEmpty(info.getBody())) {
            holder.content.setText(Html.fromHtml(info.getBody().trim()));
        }
        holder.likesCount.setText(String.valueOf(info.getLikesCount()));
        holder.time.setText(DateTimeUtil.formatDate(info.getCreateTime()));
    }

    private String getFormatString(int resId, Object obj) {
        return String.format(mContext.getResources().getString(resId), obj);
    }

    class Holder extends RecyclerView.ViewHolder {
        TextView userName, time, content;
        IconText likesCount;
        ImageView userAvatar;
        public Holder(View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.user_name);
            time = (TextView) itemView.findViewById(R.id.time);
            likesCount = (IconText) itemView.findViewById(R.id.likes_count);
            content = (TextView) itemView.findViewById(R.id.content);
            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
        }
    }
}
