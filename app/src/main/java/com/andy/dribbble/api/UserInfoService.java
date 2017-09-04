package com.andy.dribbble.api;

import com.andy.dribbble.beans.UserInfo;

import retrofit2.Callback;

/**
 * Created by andy on 2017/1/9.
 */
public class UserInfoService {

    private static final Class<UserInfoInterface> CLASS = UserInfoInterface.class;
    private static final String TOKEN = ApiUtil.getToken();

    public static void follow(int userId, Callback<Object> callback) {
        ApiUtil.getApi(CLASS).follow(userId, TOKEN).enqueue(callback);
    }

    public static void getUserInfo(Callback<UserInfo> callback) {
        ApiUtil.getApi(CLASS).getUserInfo(TOKEN).enqueue(callback);
    }
}
