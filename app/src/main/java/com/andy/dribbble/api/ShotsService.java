package com.andy.dribbble.api;

import com.andy.dribbble.beans.ShotInfo;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by andy on 2016/12/9.
 */
public class ShotsService {

    private static final Class<ShotsInterface> CLASS = ShotsInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;
    private static final int PAGE_SIZE = 10;

    public static void getShotsList(int page, String list, String timeFrame, String time, String sort, Callback<List<ShotInfo>> callback) {
        ApiUtil.getApi(CLASS).getShotsList(TOKEN, PAGE_SIZE, page, list, timeFrame, time, sort).enqueue(callback);
    }

    public static void getShotInfo(int id, Callback<ShotInfo>callback) {
        ApiUtil.getApi(CLASS).getShotInfo(TOKEN, id).enqueue(callback);
    }


}