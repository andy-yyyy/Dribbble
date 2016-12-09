package com.andy.baesapp.api;

import com.andy.baesapp.beans.GetShotsListResult;
import com.andy.baesapp.beans.ShotInfo;
import com.andy.baesapp.beans.User;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by andy on 2016/12/9.
 */
public class ShotsService {

    private static final Class<ShotsInterface> CLASS = ShotsInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;

    public static void getShotsList(String list, String timeFrame, String time, String sort, Callback<List<ShotInfo>> callback) {
        ApiUtil.getApi(CLASS).getShotsList(TOKEN, list, timeFrame, time, sort).enqueue(callback);
    }

    public static void getShotById(int id, Callback<ShotInfo>callback) {
        ApiUtil.getApi(CLASS).getShotInfo(TOKEN, id).enqueue(callback);
    }

    public static void getUserInfo(Callback<User> callback) {
        ApiUtil.getApi(CLASS).getUserInfo(TOKEN).enqueue(callback);
    }

}
