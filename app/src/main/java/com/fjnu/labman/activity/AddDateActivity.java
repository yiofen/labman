package com.fjnu.labman.activity;

import android.content.Intent;
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
import com.fjnu.labman.Adapter.AddDataAdapter;
import com.fjnu.labman.Adapter.UpdateDataAdapter;
import com.fjnu.labman.BuildConfig;
import com.fjnu.labman.R;
import com.fjnu.labman.listener.DialogOnLoadListener;
import com.fjnu.labman.utils.CommonUtil;
import com.fjnu.labman.utils.DBUtil;
import com.fjnu.labman.utils.StrUtil;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class AddDateActivity extends AbActivity {

    @AbIocView(id = R.id.add_cancel_btn, click = "cancelAdd")private Button cancelBtn;
    @AbIocView(id = R.id.add_btn, click = "dataAdd")private Button addBtn;
    @AbIocView(id = R.id.add_list)private ListView addListView;

    private AbLoadDialogFragment mDialogFragment = null;

    private List<String> listItems;
    private int tag;//判断是哪个表格
    private String[] tableId = {"equt_id", "type"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_table);

        Intent intent = getIntent();
        Bundle mExtras = intent.getExtras();
        tag = (int) mExtras.get("add");

        initListView();
        AddDataAdapter adapter = new AddDataAdapter(AddDateActivity.this, R.layout.add_list_item, listItems);
        addListView.setAdapter(adapter);
    }

    private void initListView() {
        int length = CommonUtil.table_title[tag].length-1;
        listItems = new ArrayList<>();
        for(int i=0;i<length;i++){
            listItems.add(CommonUtil.table_title[tag][i+1]+"：");
        }
    }

    public void cancelAdd(View v) {
        finish();
    }

    public void dataAdd(View v) {
        int length = addListView.getChildCount();
        EditText[] editTexts = new EditText[10];
        StringBuilder sb = new StringBuilder();

        for(int i=0;i<length;i++) {
            View view = addListView.getChildAt(i);
            editTexts[i] = view.findViewById(R.id.add_edit_text);
            if(i == length-1) {
                sb.append(editTexts[i].getText());
            } else {
                sb.append(editTexts[i].getText() + ",");
            }
        }
        String[] info = sb.toString().split(",");
        if(!StrUtil.checkInfo(AddDateActivity.this, editTexts, info)) {
            return;
        }
        add(info);
    }

    private void add(String[] info) {
        Class clazz;
        try {
            List<?> list = DBUtil.querylist(this, CommonUtil.table_type[tag], null,
                    tableId[tag]+"=?", new String[]{info[0]},
                    null, null, null, null);
            if(list.isEmpty()) {
                clazz = Class.forName(CommonUtil.table_type[tag]);
                Constructor constructor = clazz.getConstructor(new Class[]{String.class, String.class, String.class});
                Object object = constructor.newInstance(info);
                boolean flag = DBUtil.insert(AddDateActivity.this, clazz, object);
                if(flag) {
                    mDialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "正在添加数据。。。");
                    mDialogFragment.setAbDialogOnLoadListener(new DialogOnLoadListener(AddDateActivity.this, this));
                }
            } else {
                AbToastUtil.showToast(this, "数据添加失败，请重试。");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
