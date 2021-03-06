/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.beans;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by lixn on 2016/12/8.
 */

public class ImagesInfo implements Serializable {
    @SerializedName("hidpi")
    private String hidpi;
    @SerializedName("normal")
    private String normal;
    @SerializedName("teaser")
    private String teaser;

    public String getHidp() {
        return hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setHidp(String hidp) {
        this.hidpi = hidp;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }
}
