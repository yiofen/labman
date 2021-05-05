package com.fjnu.labman.listener;

import android.app.Activity;
import android.content.Context;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;

public class RefreshListener implements OnHeaderRefreshListener, OnFooterLoadListener {
    private Activity mActivity;
    private Context mContext;
    private int tag;

    public RefreshListener() {
    }

    public RefreshListener(Activity mActivity, Context mContext, int tag) {
        this.mActivity = mActivity;
        this.mContext = mContext;
        this.tag = tag;
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {

    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {

    }
}
