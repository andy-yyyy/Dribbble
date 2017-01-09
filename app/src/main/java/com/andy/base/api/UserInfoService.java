package com.andy.base.api;

import retrofit2.Callback;

/**
 * Created by andy on 2017/1/9.
 */
public class UserInfoService {

    private static final Class<UserInfoInterface> CLASS = UserInfoInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;

    public static void follow(int userId, Callback<Object> callback) {
        ApiUtil.getApi(CLASS).follow(TOKEN, userId).enqueue(callback);
    }

}
