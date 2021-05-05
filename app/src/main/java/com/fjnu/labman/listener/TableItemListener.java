package com.fjnu.labman.listener;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ab.util.AbDialogUtil;
import com.fjnu.labman.R;
import com.fjnu.labman.activity.MagtActivity;
import com.fjnu.labman.utils.StyleUtil;

public class TableItemListener implements View.OnLongClickListener, View.OnTouchListener {
    private Context mContext;
    private Activity mActivity;
    private TableLayout mTableLayout;
    private TableRow mTableRow;
    private int tag;

    public TableItemListener() {
        super();
    }

    public TableItemListener(Activity activity, Context context, TableLayout tableLayout, TableRow tableRow) {
        super();
        this.mActivity = activity;
        this.mContext = context;
        this.mTableLayout = tableLayout;
        this.mTableRow = tableRow;
    }

    public TableItemListener(Activity activity, Context context, TableLayout tableLayout, TableRow tableRow, int tag) {
        super();
        this.mActivity = activity;
        this.mContext = context;
        this.mTableLayout = tableLayout;
        this.mTableRow = tableRow;
        this.tag = tag;
    }

    @Override
    public boolean onLongClick(View v) {
        int length = mTableRow.getChildCount()-1;
        StringBuilder sb = new StringBuilder();
        TextView textView;
        for(int i=0;i<length;i++) {
            textView = (TextView) mTableRow.getVirtualChildAt(i+1);
            if(i == length-1) {
                sb.append(textView.getText());
            } else {
                sb.append(textView.getText() + ",");
            }
        }
//        AbToastUtil.showToast(mContext, sb.toString());

        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_button_listview,null);
        AbDialogUtil.showDialog(view, R.animator.fragment_top_enter,
                                      R.animator.fragment_top_exit,
                                      R.animator.fragment_pop_top_enter,
                                      R.animator.fragment_pop_top_exit);
        final ListView listView = (ListView) view.findViewById(R.id.list);
        final String[] mStrings = {
                "添加一条新数据", "修改这一条数据", "删除这一条数据"
        };
        listView.setAdapter(new ArrayAdapter<String>(mContext, R.layout.dialog_list_item, mStrings));
        Button leftBtn = (Button) view.findViewById(R.id.left_btn);
        Button rightBtn = (Button) view.findViewById(R.id.right_btn);

        listView.setOnItemClickListener(new DialogListener(mActivity, mContext, tag, sb));

        leftBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(mContext);
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AbDialogUtil.removeDialog(mContext);
            }
        });
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int count = mTableLayout.getChildCount();
        for(int i=0;i<count;i++) {
            StyleUtil.changeItemColor(mTableLayout, mTableRow, i);
        }
        return false;
    }
}
