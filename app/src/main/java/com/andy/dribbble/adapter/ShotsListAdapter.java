package com.andy.dribbble.adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.andy.dribbble.R;
import com.andy.dribbble.api.ShotsService;
import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.common_utils.DateTimeUtil;
import com.andy.dribbble.common_utils.ScreenUtil;
import com.andy.dribbble.view.IconText;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListAdapter extends BaseListAdapter<ShotInfo, ShotsListAdapter.Holder> {

    public static final String TAG_LIKE = "tag_like";
    private Fragment mFrag;
    private ActionListener mListener;

    public ShotsListAdapter(Fragment frag) {
        super(frag.getContext());
        this.mFrag = frag;
    }

    public void setActionListener(ActionListener listener) {
        this.mListener = listener;
    }

    @Override
    protected Holder onCreateItemHolder(ViewGroup parent) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shots_list, parent, false));
    }

    @Override
    protected void onBindItemHolder(final Holder holder, int position) {
        super.onBindItemHolder(holder, position);
        final ShotInfo info = mData.get(position);
        if (info == null) {
            return;
        }

        holder.updateTime.setText(DateTimeUtil.formatDate(info.getUpdateTime()));
        holder.likesCount.setText(String.valueOf(info.getLikesCount()));
        holder.commentsCount.setText(String.valueOf(info.getCommentsCount()));
        holder.viewsCount.setText(String.valueOf(info.getViewsCount()));

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

        ShotsService.checkShot(info.getId(), new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                if (response.isSuccessful()) {
                    holder.likesCount.setIconRes(R.mipmap.ic_like_fill);
                    ImageView iconView = (ImageView) holder.likesCount.findViewById(R.id.icon);
                    iconView.setTag(TAG_LIKE);
                } else {
                    holder.likesCount.setIconRes(R.mipmap.ic_like);
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                holder.likesCount.setIconRes(R.mipmap.ic_like);
            }
        });

        if (mListener != null) {
            holder.likesCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateLikeView(holder, info);
                    mListener.onLikeClick(view, info.getId());
                }
            });
        }

    }

    private String getFormatString(int resId, Object obj) {
        return String.format(mFrag.getContext().getResources().getString(resId), obj);
    }

    private void updateLikeView(Holder holder, ShotInfo info) {
        ImageView iconView = (ImageView) holder.likesCount.findViewById(R.id.icon);
        Object tag = iconView.getTag();
        if (TAG_LIKE.equals(tag)) {
            holder.likesCount.setText(Integer.toString(info.getLikesCount()));
            holder.likesCount.setIconRes(R.mipmap.ic_like);
            iconView.setTag(null);
        } else {
            holder.likesCount.setText(Integer.toString(info.getLikesCount()+1));
            holder.likesCount.setIconRes(R.mipmap.ic_like_fill);
            iconView.setTag(TAG_LIKE);
        }
        Animation anim = new ScaleAnimation(0f, 1.2f, 0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(200);
        iconView.startAnimation(anim);
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView image;
        IconText updateTime, likesCount, commentsCount, viewsCount;
        public Holder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.image);
            updateTime = (IconText) itemView.findViewById(R.id.update_time);
            likesCount = (IconText) itemView.findViewById(R.id.likes_count);
            commentsCount = (IconText) itemView.findViewById(R.id.comments_count);
            viewsCount = (IconText) itemView.findViewById(R.id.views_count);
        }
    }

    public interface ActionListener {
        void onLikeClick(View view, int shotId);
    }
}
