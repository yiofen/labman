package com.fjnu.labman.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbDialogUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.fjnu.labman.R;

public class PullToRefresh extends LinearLayout implements OnHeaderRefreshListener, OnFooterLoadListener {
    private Context mContext;
    private AbPullToRefreshView mAbPullToRefreshView;

    public PullToRefresh(Context context) {
        super(context);
    }

    public PullToRefresh(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.pull_to_refresh, null, true);
        mAbPullToRefreshView = (AbPullToRefreshView) view.findViewById(R.id.mPullRefreshView);
        mAbPullToRefreshView.setOnHeaderRefreshListener(this);
        mAbPullToRefreshView.setOnFooterLoadListener(this);
        mAbPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular, null));
        mAbPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular, null));

    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        refreshTask();
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        loadMoreTask();
    }

    public void refreshTask(){
        AbTask mAbTask = AbTask.newInstance();
        final AbTaskItem item = new AbTaskItem();
        item.setListener(new AbTaskListener() {
            @Override
            public void update() {
                AbDialogUtil.removeDialog(mContext);
                mAbPullToRefreshView.onHeaderRefreshFinish();
            }

            @Override
            public void get() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mAbTask.execute(item);
    }

    public void loadMoreTask(){
        AbTask mAbTask = AbTask.newInstance();
        final AbTaskItem item = new AbTaskItem();
        item.setListener(new AbTaskListener() {

            @Override
            public void update() {
                AbDialogUtil.removeDialog(mContext);
                mAbPullToRefreshView.onFooterLoadFinish();
            }

            @Override
            public void get() {
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
        });

        mAbTask.execute(item);
    }
}
