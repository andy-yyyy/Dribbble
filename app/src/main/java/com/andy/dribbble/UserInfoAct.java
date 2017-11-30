package com.andy.dribbble;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.andy.dribbble.api.ApiUtil;
import com.andy.dribbble.api.UserInfoService;
import com.andy.dribbble.beans.UserInfo;
import com.andy.dribbble.common_utils.ToastUtil;
import com.andy.dribbble.view.LinearButton;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andy on 2017/1/7.
 */
public class UserInfoAct extends BaseMDActivity {

    public static final String TAG_USER_INFO = "user_info";
    public static final String TAG_IS_CURRENT_USER = "is_current_user";
    private UserInfo mUserInfo;
    private boolean mIsCurrentUser;

    private ImageView mAvatar;
    private TextView mName;
    private Button mFollow;
    private LinearButton mFollowerCount, mFollowingsCount, mLikesCount;

    public static Intent getIntent(Context context) {
        return new Intent(context, UserInfoAct.class);
    }
    public static Intent getIntent(Context context, boolean isCurrent) {
        Intent intent = new Intent(context, UserInfoAct.class);
        intent.putExtra(TAG_IS_CURRENT_USER, isCurrent);
        return intent;
    }

    public static Intent getIntent(Context context, UserInfo userInfo) {
        Intent intent = new Intent(context, UserInfoAct.class);
        intent.putExtra(TAG_USER_INFO, userInfo);
        return intent;
    }


    @Override
    protected int getContentLayout() {
        return R.layout.act_user_info;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putSerializable(TAG_USER_INFO, mUserInfo);
    }

    @Override
    protected void initView() {
        super.initView();
        mAvatar = (ImageView) findViewById(R.id.avatar);
        mName = (TextView) findViewById(R.id.name);
        mFollow = (Button) findViewById(R.id.follow);
        mFollowerCount = (LinearButton) findViewById(R.id.followers_count);
        mFollowingsCount = (LinearButton) findViewById(R.id.followings_count);
        mLikesCount = (LinearButton) findViewById(R.id.likes_count);
        updateUseInfo();
    }

    private void updateUseInfo() {
        if (mUserInfo != null) {
            Glide.with(this).load(mUserInfo.getAvatarUrl()).into(mAvatar);
            mToolBar.setTitle(mUserInfo.getName());
            mImage.setImageResource(R.mipmap.bg_user_info);
            mName.setText(mUserInfo.getName());
            mFollowerCount.update(mUserInfo.getFollowersCount() + "", "Followers");
            mFollowingsCount.update(mUserInfo.getFollowingsCount() + "", "Followings");
            mLikesCount.update(mUserInfo.getLikesCount() + "", "Likes");
            mFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO: 2017/1/7 follow action
                    int id = mUserInfo.getId();
                    if (ApiUtil.hasUserToken()) {
                        UserInfoService.follow(id, new Callback<Object>() {
                            @Override
                            public void onResponse(Call<Object> call, Response<Object> response) {
                                ToastUtil.show("关注成功！");
                                String msg = response.message();
                                Log.d("TAG", response.message());
                            }

                            @Override
                            public void onFailure(Call<Object> call, Throwable t) {
                                Log.d("TAG", t.toString());
                            }
                        });
                    }
                }
            });
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        Object isCurrentUser;
        if (savedInstanceState == null) {
            mUserInfo = (UserInfo) getIntent().getSerializableExtra(TAG_USER_INFO);
            isCurrentUser =  getIntent().getSerializableExtra(TAG_IS_CURRENT_USER);
        } else {
            mUserInfo = (UserInfo) savedInstanceState.getSerializable(TAG_USER_INFO);
            isCurrentUser = savedInstanceState.getSerializable(TAG_IS_CURRENT_USER);
        }
        if (isCurrentUser instanceof Boolean) {
            mIsCurrentUser = (boolean) isCurrentUser;
        }

        if (mIsCurrentUser) {
            mUserInfo = CacheUtil.fetchUserInfo(this);
            if (mUserInfo == null && ApiUtil.hasUserToken()) {
                UserInfoService.getUserInfo(new Callback<UserInfo>() {
                    @Override
                    public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                        if (response.isSuccessful()) {
                            mUserInfo = response.body();
                            CacheUtil.cacheUserInfo(UserInfoAct.this, mUserInfo);
                            updateUseInfo();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserInfo> call, Throwable t) {
                        ToastUtil.show(t.getMessage());
                    }
                });
            }
        }
    }
}
