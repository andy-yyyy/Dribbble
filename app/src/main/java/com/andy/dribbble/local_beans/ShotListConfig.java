/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.local_beans;

import java.io.Serializable;

/**
 * Created by lixn on 2017/9/5.
 */

public class ShotListConfig implements Serializable {

    public final String listType;
    public final String timeFrame;
    public final String date;
    public final String sort;

    private ShotListConfig(Builder builder) {
        this.listType = builder.listType;
        this.timeFrame = builder.timeFrame;
        this.date = builder.date;
        this.sort = builder.sort;
    }

    public static class Builder {
        private String listType;
        private String timeFrame;
        private String date;
        private String sort;

        public ShotListConfig build() {
            return new ShotListConfig(this);
        }
        public Builder listType(String type) {
            this.listType = type;
            return this;
        }

        public Builder timeFrame(String timeFrame) {
            this.timeFrame = timeFrame;
            return this;
        }

        public Builder date(String date) {
            this.date = date;
            return this;
        }

        public Builder sort(String sort) {
            this.sort = sort;
            return this;
        }
    }

}
