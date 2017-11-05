package com.andy.dribbble.api.net_interface;

import com.andy.dribbble.beans.CommentInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by andy on 2016/12/26.
 */
public interface CommentsInterface {

    @GET("v1/shots/{shot_id}/comments")
    Call<List<CommentInfo>> getCommentsList(
            @Query("access_token") String token,
            @Query("per_page") int pageSize,
            @Query("page") int page,
            @Query("shot_id") int shotId
    );

}
