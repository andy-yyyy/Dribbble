package com.andy.dribbble.api;

import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.beans.UserInfo;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by andy on 2017/1/9.
 */
public class UserInfoService {

    private static final Class<UserInfoInterface> CLASS = UserInfoInterface.class;
    private static final String TOKEN = ApiUtil.getToken();
    private static final int PAGE_SIZE = 10;

    public static void follow(int userId, Callback<Object> callback) {
        ApiUtil.getApi(CLASS).follow(userId, TOKEN).enqueue(callback);
    }

    public static void getUserInfo(Callback<UserInfo> callback) {
        ApiUtil.getApi(CLASS).getUserInfo(TOKEN).enqueue(callback);
    }

    public static void getUserShots(int page, int userId, Callback<List<ShotInfo>> callback) {
        ApiUtil.getApi(CLASS).getUserShots(userId, TOKEN, PAGE_SIZE, page).enqueue(callback);
    }
}
