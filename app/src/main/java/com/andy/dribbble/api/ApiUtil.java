package com.andy.dribbble.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.andy.dribbble.common_utils.App;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by andy on 2016/12/9.
 */
public class ApiUtil {

    public static final String KEY_USER_INFO = "user_info";
    public static final String KEY_USER_ACCESS_TOKEN = "user_access_token";

    private static SharedPreferences mSp = App.getContext().getSharedPreferences(KEY_USER_INFO, Context.MODE_PRIVATE);
    private static String mToken;

    public static <T> T getApi(Class<T> cls) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(cls);
    }

    public static boolean hasToken() {
        return !TextUtils.isEmpty(getToken());
    }

    public static String getToken() {
        if (!TextUtils.isEmpty(mToken)) {
            return mToken;
        }
        mToken = getTokenFromSp();
        if (!TextUtils.isEmpty(mToken)) {
            return mToken;
        }
        return null;
    }

    public static String getTokenFromSp() {
        return mSp.getString(KEY_USER_ACCESS_TOKEN, null);
    }

    public static void saveTokenToSp(String token) {
        mSp.edit().putString(KEY_USER_ACCESS_TOKEN, token).apply();
    }

}
