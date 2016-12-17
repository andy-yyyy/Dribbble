package com.andy.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以添加Header和Footer的列表适配器
 * Created by andy on 2016/12/15.
 */
public abstract class ExtensibleListAdapter<D, H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final String TAG = this.getClass().getSimpleName();
    protected static final int TYPE_ITEM = 0;
    protected static final int TYPE_HEADER = 1;
    protected static final int TYPE_FOOTER = 2;

    protected View mHeaderView;
    protected View mFooterView;
    protected Context mContext;
    protected List<D> mData = new ArrayList<>();

    protected boolean mHeaderEnabled = false;
    protected boolean mFooterEnabled = false;

    public ExtensibleListAdapter(Context context) {
        this(context, new ArrayList<D>());
        this.mHeaderEnabled = isHeaderEnabled();
        this.mFooterEnabled = isFooterEnabled();
    }

    public ExtensibleListAdapter(Context context, List<D> data) {
        this.mContext = context;
        this.mData = data;
    }

    protected abstract H onCreateItemHolder(ViewGroup parent);
    protected abstract void onBindItemHolder(H itemHolder, int position);

    protected View onCreateHeader(ViewGroup parent) {
        return null;
    }

    protected View onCreateFooter(ViewGroup parent) {
        return null;
    }

    public void setHeaderView(View headerView) {
        this.mHeaderView = headerView;
    }

    public void setFooterView(View footerView) {
        this.mFooterView = footerView;
    }

    public void setHeaderViewEnabled(boolean enabled) {
        this.mHeaderEnabled = enabled;
    }

    public boolean isHeaderEnabled() {
        return mHeaderEnabled;
    }

    public boolean isFooterEnabled() {
        return mFooterEnabled;
    }

    public void setFooteViewEnabled(boolean enabled) {
        this.mFooterEnabled = enabled;
    }

    public void updateData(List<D> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    public int getDataPosition(int viewPosition) {
        int pos = viewPosition;
        if (isHeaderEnabled()) {
            pos = viewPosition -1;
        }
        if (pos < 0) {
            pos = 0;
        }
        if (pos > mData.size() -1) {
            pos = mData.size() - 1;
        }
        return pos;
    }

    @Override
    public int getItemCount() {
        int size = mData.size();
        if (isFooterEnabled()) {
            ++size;
        }
        if (isHeaderEnabled()) {
            ++size;
        }
        Log.d(TAG, "item count>>"+size);
        return size;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderEnabled() && position == 0) {
            return TYPE_HEADER;
        }
        if (isFooterEnabled() && position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = onCreateItemHolder(parent);
        switch (viewType) {
            case TYPE_ITEM:
                holder = onCreateItemHolder(parent);
                break;
            case TYPE_HEADER:
                mHeaderView = onCreateHeader(parent);
                if (mHeaderView != null) {
                    holder = new HeaderHolder(mHeaderView);
                }
                break;
            case TYPE_FOOTER:
                mFooterView = onCreateFooter(parent);
                if (mFooterView != null) {
                    holder = new FooterHolder(mFooterView);
                }
                break;
            default:break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType) {
            case TYPE_ITEM:
                onBindItemHolder((H) holder, getDataPosition(position));
                break;
            case TYPE_HEADER:
                Log.d(TAG, "onBindViewHolder>>TYPE_HEADER");
                break;
            case TYPE_FOOTER:
                Log.d(TAG, "onBindViewHolder>>TYPE_FOOTER");
                break;
            default: break;
        }
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder{
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder{
        public FooterHolder(View itemView) {
            super(itemView);
        }
    }
}
