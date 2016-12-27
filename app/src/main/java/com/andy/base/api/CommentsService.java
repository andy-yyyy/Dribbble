package com.andy.base.api;

import com.andy.base.beans.CommentInfo;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by andy on 2016/12/27.
 */
public class CommentsService {

    private static final Class<CommentsInterface> CLASS = CommentsInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;
    private static final int PAGE_SIZE = 20;

    public static void getCommentsList(int page, int shotId, Callback<List<CommentInfo>> callback) {
        ApiUtil.getApi(CLASS).getCommentsList(TOKEN, PAGE_SIZE, page, shotId).enqueue(callback);
    }
}
