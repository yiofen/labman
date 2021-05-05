package com.fjnu.labman.listener;

import android.widget.TabHost;
import android.widget.TabWidget;

import com.fjnu.labman.utils.StyleUtil;

public class TabChangedListener implements TabHost.OnTabChangeListener {

    private TabHost mTabHost;
    private TabWidget mTabWidget;

    public TabChangedListener() {
        super();
    }

    public TabChangedListener(TabHost tabHost, TabWidget tabWidget) {
        super();
        this.mTabHost = tabHost;
        this.mTabWidget = tabWidget;
    }

    @Override
    public void onTabChanged(String tabId) {
        int count = mTabHost.getTabWidget().getChildCount();
        for(int i=0;i<count;i++) {
            StyleUtil.changeTabColor(mTabHost, mTabWidget, i);
        }
    }
}