package com.andy.dribbble.contract;

/**
 * Created by andy on 2016/12/11.
 */
public class BaseListContract {
    public interface View {
        void showRefreshView(boolean refreshing);
        void showLoadMoreView(boolean show);
    }
    public interface Presenter {

    }
}
