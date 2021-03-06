package com.andy.dribbble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.dribbble.api.ShotsService;
import com.andy.dribbble.beans.CommentInfo;
import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.common_utils.DateTimeUtil;
import com.andy.dribbble.view.IconText;
import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2016/12/20.
 */
public class ShotDetailFrag extends BaseFragment {

    public static final String TAG_LIKE = "tag_like";
    private ShotInfo mShotInfo;

    public static ShotDetailFrag getInstance(ShotInfo shotInfo) {
        ShotDetailFrag frag = new ShotDetailFrag();
        Bundle bundle = new Bundle();
        bundle.putSerializable(ShotDetailAct.SHOT_INFO, shotInfo);
        frag.setArguments(bundle);
        return frag;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_shot_detail_head, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView(view);
    }

    private void initView(View view) {

        IconText updateTime = (IconText) view.findViewById(R.id.update_time);
        final IconText likesCount = (IconText) view.findViewById(R.id.likes_count);
        IconText commentsCount = (IconText) view.findViewById(R.id.comments_count);
        IconText viewsCount = (IconText) view.findViewById(R.id.views_count);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView userName = (TextView) view.findViewById(R.id.user_name);
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);

        if (mShotInfo != null) {
            updateTime.setText(DateTimeUtil.formatDate(mShotInfo.getUpdateTime()));
            likesCount.setText(String.valueOf(mShotInfo.getLikesCount()));
            commentsCount.setText(String.valueOf(mShotInfo.getCommentsCount()));
            viewsCount.setText(String.valueOf(mShotInfo.getViewsCount()));
            title.setText(mShotInfo.getTitle());
            name.setText(mShotInfo.getUserInfo().getName());
            userName.setText(mShotInfo.getUserInfo().getUserName());
            if (mShotInfo.getDescription() != null) {
                description.setText(Html.fromHtml(mShotInfo.getDescription()));
            }
            if (mShotInfo.getUserInfo() != null) {
                Glide.with(getContext()).load(mShotInfo.getUserInfo().getAvatarUrl()).into(avatar);
            }
            avatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(UserInfoAct.getIntent(getContext(), mShotInfo.getUserInfo()));
                }
            });
            ShotsService.checkLike(mShotInfo.getId(), new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    if (response.isSuccessful()) {
                        likesCount.setIconRes(R.mipmap.ic_like_fill);
                        likesCount.setTag(TAG_LIKE);
                    } else {
                        likesCount.setIconRes(R.mipmap.ic_like);
                        likesCount.setTag(null);
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    likesCount.setIconRes(R.mipmap.ic_like);
                    likesCount.setTag(null);
                }
            });
            likesCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    toggleLikeStatus(likesCount);
                    Object tag = likesCount.getTag();
                    if (TAG_LIKE.equals(tag)) {
                        ShotsService.unlikeShot(mShotInfo.getId(), new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {

                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                            }
                        });
                    } else {

                        ShotsService.likeShot(mShotInfo.getId(), new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {

                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                            }
                        });
                    }
                }
            });
        }

    }

    private void toggleLikeStatus(IconText likeCount) {
        Object tag = likeCount.getTag();
        if (TAG_LIKE.equals(tag)) {
            likeCount.setIconRes(R.mipmap.ic_like);
            likeCount.setText(String.valueOf(mShotInfo.getLikesCount()));
            likeCount.setTag(null);
        } else {
            likeCount.setIconRes(R.mipmap.ic_like_fill);
            likeCount.setText(String.valueOf(mShotInfo.getLikesCount() + 1));
            likeCount.setTag(TAG_LIKE);
        }
        Animation anim = new ScaleAnimation(0f, 1.2f, 0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,  Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(200);
        ImageView iconView = (ImageView) likeCount.findViewById(R.id.icon);
        iconView.startAnimation(anim);
    }

    private String getFormatString(int resId, Object obj) {
        return String.format(getContext().getResources().getString(resId), obj);
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mShotInfo = (ShotInfo) bundle.getSerializable(ShotDetailAct.SHOT_INFO);
        }
        Log.d("TAG","shot info>>"+mShotInfo);

        if (mShotInfo != null) {
            int id = mShotInfo.getId();
            ShotsService.getCommentsList(1, id, new Callback<List<CommentInfo>>() {
                @Override
                public void onResponse(Call<List<CommentInfo>> call, Response<List<CommentInfo>> response) {
                    List<CommentInfo> list = response.body();
                    Log.d("TAG", "res: "+response);
                }

                @Override
                public void onFailure(Call<List<CommentInfo>> call, Throwable t) {
                    Log.d("TAG", "res: "+call);
                }
            });
        }
    }
}
