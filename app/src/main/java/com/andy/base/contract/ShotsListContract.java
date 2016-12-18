package com.andy.base.contract;

import com.andy.base.beans.ShotInfo;

import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListContract {
    public interface View extends BaseListContract.View {
        void refreshView(List<ShotInfo> infoList);
        void refreshMoreView(List<ShotInfo> moreList);
    }

    public interface Presenter {
        void updateData(int page, String list, String timeFrame, String time, String sort);
        void loadMoreData(int page, String list, String timeFrame, String time, String sort);
    }
}
