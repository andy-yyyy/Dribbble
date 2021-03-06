/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lixn on 2016/12/8.
 */

public class LinksInfo implements Serializable {
    @SerializedName("web")
    private String web;
    @SerializedName("twitter")
    private String twitter;

    public String getWeb() {
        return web;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }
}
