package com.andy.base.contract;

import com.andy.base.beans.CommentInfo;

import java.util.List;

/**
 * Created by andy on 2016/12/28.
 */
public class CommentsListContract {
    public interface View extends BaseListContract.View {
        void refreshView(List<CommentInfo> infoList);
        void refreshMoreView(List<CommentInfo> moreList);
    }

    public interface Presenter {
        void updateData(int page, int shotId);
        void loadMoreData(int page, int shotId);
    }
}
