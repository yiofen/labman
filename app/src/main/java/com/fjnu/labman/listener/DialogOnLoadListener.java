package com.fjnu.labman.listener;

import android.app.Activity;
import android.content.Context;

import com.ab.fragment.AbDialogFragment;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;

public class DialogOnLoadListener implements AbDialogFragment.AbDialogOnLoadListener {
    private Activity mActivity;
    private Context mContext;

    public DialogOnLoadListener() {
    }

    public DialogOnLoadListener(Activity mActivity, Context mContext) {
        this.mActivity = mActivity;
        this.mContext = mContext;
    }

    @Override
    public void onLoad() {
        load();
    }

    private void load() {
        AbTask mAbTask = AbTask.newInstance();
        final AbTaskItem item = new AbTaskItem();

        item.setListener(new AbTaskListener() {
            @Override
            public void update() {
                mActivity.finish();
            }

            @Override
            public void get() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mAbTask.execute(item);
    }
}
