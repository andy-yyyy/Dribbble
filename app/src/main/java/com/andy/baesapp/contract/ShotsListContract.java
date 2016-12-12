package com.andy.baesapp.contract;

import com.andy.baesapp.beans.ShotInfo;

import java.util.List;

/**
 * Created by andy on 2016/12/11.
 */
public class ShotsListContract {
    public interface View extends BaseListContract.View {
        void refreshView(List<ShotInfo> infoList);
    }

    public interface Presenter {
        void updateData(int page, String list, String timeFrame, String time, String sort);
    }
}
