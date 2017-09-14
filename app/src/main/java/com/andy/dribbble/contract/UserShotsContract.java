/*
 * Copyright (C) 2017 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.andy.dribbble.contract;

import com.andy.dribbble.beans.ShotInfo;

import java.util.List;

/**
 * Created by lixn on 2017/9/14.
 */

public class UserShotsContract {

    public interface View extends BaseListContract.View {
        void refreshView(List<ShotInfo> infoList);
        void refreshMoreView(List<ShotInfo> moreList);
    }
    public interface Presenter extends BaseListContract.Presenter {
        void updateData(int page, int userId);
        void loadMoreData(int page, int userId);
    }
}
