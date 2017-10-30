/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble;

import android.content.Context;

import com.andy.dribbble.beans.CommentInfo;
import com.andy.dribbble.beans.ShotInfo;
import com.andy.dribbble.beans.UserInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

/**
 * 缓存工具
 * Created by lixn on 2017/8/17.
 */

public class CacheUtil {

    public static final String FILE_NAME_SHOT_LIST = "shot_list";
    public static final String FILE_NAME_COMMENT_LIST = "comment_list";
    public static final String FILE_NAME_CURRENT_USER_INFO = "current_user_info";

    public static UserInfo fetchUserInfo(Context context) {
        String json = fetchStringData(context, FILE_NAME_CURRENT_USER_INFO);
        Gson gson = new Gson();
        return gson.fromJson(json, UserInfo.class);
    }

    public static void cacheUserInfo(Context context, UserInfo userInfo) {
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        saveStringData(context, FILE_NAME_CURRENT_USER_INFO, json);
    }

    public static void cacheShotList(Context context, List<ShotInfo> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        saveStringData(context, FILE_NAME_SHOT_LIST, json);
    }

    public static List<ShotInfo> fetchCacheShotList(Context context) {
        String json = fetchStringData(context, FILE_NAME_SHOT_LIST);
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<ShotInfo>>(){}.getType());
    }

    public static void cacheCommentList(Context context, List<CommentInfo> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        saveStringData(context, FILE_NAME_COMMENT_LIST, json);
    }

    public static List<CommentInfo> fetchCacheCommentList(Context context) {
        String json = fetchStringData(context, FILE_NAME_COMMENT_LIST);
        Gson gson = new Gson();
        UserInfo info = new CommentInfo(){}.getUserInfo();
        return gson.fromJson(json, new TypeToken<List<CommentInfo>>(){}.getType());
    }

    /**
     * 将字符串数据缓存到文件中
     * @param context
     * @param fileName
     * @param data
     */
    public static void saveStringData(Context context, String fileName, String data) {
        FileOutputStream fos;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从缓存文件中获取字符串数据
     * @param context
     * @param fileName
     * @return
     */
    public static String fetchStringData(Context context, String fileName) {
        String result = "";
        FileInputStream fis;
        try {
            fis = context.openFileInput(fileName);
            byte[] data = new byte[fis.available()];
            fis.read(data);
            result = new String(data);
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
