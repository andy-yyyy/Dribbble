package com.andy.dribbble.api;

import com.andy.dribbble.api.net_interface.ShotsInterface;
import com.andy.dribbble.beans.CommentInfo;
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

    public static void getCommentsList(int page, int shotId, Callback<List<CommentInfo>> callback) {
        ApiUtil.getApi(CLASS).getCommentsList(TOKEN, PAGE_SIZE, page, shotId).enqueue(callback);
    }

    public static void likeShot(int shotId, Callback<Object> callback) {
        ApiUtil.getApi(CLASS).likeShot(shotId, TOKEN).enqueue(callback);
    }

    public static void checkLike(int shotId, Callback<Object> callback) {
        ApiUtil.getApi(CLASS).checkLike(shotId, TOKEN).enqueue(callback);
    }
}
