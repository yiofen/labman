package com.fjnu.labman.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.ab.fragment.AbSampleDialogFragment;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.pullview.AbPullToRefreshView;
import com.fjnu.labman.R;
import com.fjnu.labman.activity.AddDateActivity;
import com.fjnu.labman.activity.MagtActivity;
import com.fjnu.labman.activity.UpdateActivity;
import com.fjnu.labman.utils.CommonUtil;
import com.fjnu.labman.utils.DBUtil;

import java.lang.reflect.Method;
import java.util.List;

public class DialogListener implements AdapterView.OnItemClickListener {

    private Context mContext;
    private Activity mActivity;

    private MagtActivity magtActivity = null;

    private AbSampleDialogFragment mDialogFragment = null;

    private Intent mIntent;
    private String[] tableId = {"equt_id", "type"};
    private String[] datas;
    private int tag;//用于判断哪个页面

    public DialogListener() {}

    public DialogListener(Activity activity, Context context, int tag, StringBuilder sb) {
        super();
        this.mActivity = activity;
        this.magtActivity = (MagtActivity) activity;
        this.mContext = context;
        this.tag = tag;
        this.datas = sb.toString().split(",");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(tag == 0 || tag == 1) {
            if(position == 0) {//添加
                mIntent = new Intent(mActivity, AddDateActivity.class);
                mIntent.putExtra("add", tag);
                mActivity.startActivity(mIntent);
                AbDialogUtil.removeDialog(mContext);
            } else if(position == 1) {//修改
                mIntent = new Intent(mActivity, UpdateActivity.class);
                mIntent.putExtra("update", tag);
                mIntent.putExtra("data", datas);
                mActivity.startActivity(mIntent);
                AbDialogUtil.removeDialog(mContext);
            } else if(position == 2) {//删除
                AbDialogUtil.removeDialog(mContext);
                View childView = LayoutInflater.from(mContext).inflate(R.layout.dialog_button_confirm,null);
                mDialogFragment = AbDialogUtil.showDialog(childView, R.animator.fragment_top_enter,
                                                   R.animator.fragment_top_exit,
                                                   R.animator.fragment_pop_top_enter,
                                                   R.animator.fragment_pop_top_exit);

                Button leftDioBtn = (Button) childView.findViewById(R.id.left_dio_btn);
                Button rightDioBtn = (Button) childView.findViewById(R.id.right_dio_btn);

                leftDioBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AbDialogUtil.removeDialog(mContext);
                    }
                });
                rightDioBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        delete();

                    }
                });
            }
        } else if(tag == 2) {
            //用户个人信息修改界面
        } else if(tag == 3) {
            //卫生页面的内容修改
        }

    }

    private boolean delete() {
        Class clazz;
        int id;
        try {
            List<?> list = DBUtil.querylist(mContext, CommonUtil.table_type[tag], null,
                    tableId[tag]+"=?", new String[]{datas[0]},
                    null, null, null, null);
            if(!list.isEmpty()) {
                clazz = list.get(0).getClass();
                Method method = clazz.getMethod("get_id");
                id = (int) method.invoke(list.get(0));
                DBUtil.delete(mContext, clazz, id);
                magtActivity.refreshTask(new AbPullToRefreshView(mContext), true);
                AbDialogUtil.removeDialog(mContext);
                return true;
            } else {
                AbToastUtil.showToast(mContext, "数据删除失败，请重试。");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
