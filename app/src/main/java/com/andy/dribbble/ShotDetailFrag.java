package com.andy.dribbble;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.dribbble.api.CommentsService;
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
        IconText likesCount = (IconText) view.findViewById(R.id.likes_count);
        IconText commentsCount = (IconText) view.findViewById(R.id.comments_count);
        IconText viewsCount = (IconText) view.findViewById(R.id.views_count);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView description = (TextView) view.findViewById(R.id.description);
        TextView name = (TextView) view.findViewById(R.id.name);
        TextView userName = (TextView) view.findViewById(R.id.user_name);
        ImageView avatar = (ImageView) view.findViewById(R.id.avatar);

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mShotInfo != null) {
                    startActivity(UserInfoAct.getIntent(getContext(), mShotInfo.getUserInfo()));
                }
            }
        });

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
        }
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
            CommentsService.getCommentsList(1, id, new Callback<List<CommentInfo>>() {
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
