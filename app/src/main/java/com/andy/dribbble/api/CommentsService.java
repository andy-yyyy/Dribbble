package com.andy.dribbble.api;

import com.andy.dribbble.api.net_interface.CommentsInterface;
import com.andy.dribbble.beans.CommentInfo;

import java.util.List;

import retrofit2.Callback;

/**
 * Created by andy on 2016/12/27.
 */
public class CommentsService {

    private static final Class<CommentsInterface> CLASS = CommentsInterface.class;
    private static final String TOKEN = ApiConstants.CLIENT_ACCESS_TOKEN;
    private static final int PAGE_SIZE = 10;


}
