package com.fjnu.labman.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.ab.activity.AbActivity;
import com.ab.fragment.AbDialogFragment;
import com.ab.fragment.AbLoadDialogFragment;
import com.ab.task.AbTask;
import com.ab.task.AbTaskItem;
import com.ab.task.AbTaskListener;
import com.ab.util.AbDialogUtil;
import com.ab.util.AbLogUtil;
import com.ab.util.AbToastUtil;
import com.ab.view.ioc.AbIocView;
import com.fjnu.labman.Adapter.UpdateDataAdapter;
import com.fjnu.labman.R;
import com.fjnu.labman.listener.DialogOnLoadListener;
import com.fjnu.labman.utils.CommonUtil;
import com.fjnu.labman.utils.DBUtil;
import com.fjnu.labman.utils.StrUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends AbActivity {

    @AbIocView(id = R.id.update_cancel_btn, click = "cancelUpdate")private Button cancelBtn;
    @AbIocView(id = R.id.update_btn, click = "dataUpdate")private Button updateBtn;
    @AbIocView(id = R.id.update_list)private ListView updateListView;

    private AbLoadDialogFragment mDialogFragment = null;

    private List<String[]> listItems;
    private int tag;//判断是哪个表格
    private String[] tableId = {"equt_id", "type"};
    private String[] datas;//原始数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_table);

        Intent intent = getIntent();
        Bundle mExtras = intent.getExtras();
        tag = (int) mExtras.get("update");
        datas = (String[]) mExtras.get("data");

        initListView();
        UpdateDataAdapter adapter = new UpdateDataAdapter(UpdateActivity.this, R.layout.update_list_item, listItems);
        updateListView.setAdapter(adapter);
    }

    private void initListView() {
        int length = CommonUtil.table_title[tag].length-1;
        listItems = new ArrayList<>();
        for(int i=0;i<length;i++){
            listItems.add(new String[]{CommonUtil.table_title[tag][i+1]+"：", datas[i]});
        }

    }

    public void cancelUpdate(View v) {
        finish();
    }

    public void dataUpdate(View v) {
        int length = updateListView.getChildCount();
        EditText[] editTexts = new EditText[10];
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<length;i++) {
            View view = updateListView.getChildAt(i);
            editTexts[i] = view.findViewById(R.id.update_edit_text);
            if(i == length-1) {
                sb.append(editTexts[i].getText());
            } else {
                sb.append(editTexts[i].getText() + ",");
            }
        }
        String[] info = sb.toString().split(",");
        if(!StrUtil.checkInfo(UpdateActivity.this, editTexts, info)) {
            return;
        }
        update(info);
    }

    private void update(String[] info) {
        Class clazz;
        int id;
        try {
            List<?> list = DBUtil.querylist(this, CommonUtil.table_type[tag], null,
                    tableId[tag]+"=?", new String[]{datas[0]},
                    null, null, null, null);
            if(!list.isEmpty()) {
                clazz = list.get(0).getClass();
                Method method = clazz.getMethod("get_id", null);
                id = (int) method.invoke(list.get(0));
                Constructor constructor = clazz.getConstructor(new Class[]{int.class, String.class, String.class, String.class});
                Object object = constructor.newInstance(id, info[0], info[1], info[2]);
                boolean flag = DBUtil.update(UpdateActivity.this, CommonUtil.table_type[tag], object);
                if(flag) {
                    mDialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "正在修改数据。。。");
                    mDialogFragment.setAbDialogOnLoadListener(new DialogOnLoadListener(UpdateActivity.this, this));
                }
            } else {
                AbToastUtil.showToast(this, "数据修改失败，请重试。");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
