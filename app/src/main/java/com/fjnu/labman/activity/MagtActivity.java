package com.fjnu.labman.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.ab.view.sliding.AbSlidingPlayView;
import com.fjnu.labman.R;
import com.fjnu.labman.listener.TabChangedListener;
import com.fjnu.labman.listener.TableItemListener;
import com.fjnu.labman.model.Duty;
import com.fjnu.labman.model.Equipment;
import com.fjnu.labman.model.User;
import com.fjnu.labman.model.Warehouse;
import com.fjnu.labman.utils.CommonUtil;
import com.fjnu.labman.utils.DBUtil;
import com.fjnu.labman.utils.StyleUtil;

import java.util.ArrayList;
import java.util.List;

public class MagtActivity extends AbActivity implements OnHeaderRefreshListener, OnFooterLoadListener {

    @AbIocView(id = R.id.mAbSlidingPlayView)private AbSlidingPlayView mSlidingPlayView;
    @AbIocView(id = android.R.id.tabhost)private TabHost mTabHost;
    @AbIocView(id = android.R.id.tabcontent)private FrameLayout mFrameLayout;
    @AbIocView(id = android.R.id.tabs)private TabWidget mTabWidget;
    @AbIocView(id = R.id.content)private LinearLayout mContent;
    @AbIocView(id = R.id.main_sv)private ScrollView managerScroll;
    @AbIocView(id = R.id.about_sv)private ScrollView aboutScroll;

    private AbPullToRefreshView mPullToRefreshView = null;
    private AbLoadDialogFragment mDialogFragment = null;
    private TableLayout tableLayout;

    public ViewGroup.LayoutParams viewGroupParams;
    public LinearLayout.LayoutParams linearParams;
    public TableLayout.LayoutParams tableParams;
    public TableRow.LayoutParams tableRowParams;

    private List<Integer> pagerList;
    private List<String> labelList;

    private boolean isPause = false;

    public MagtActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.magt_page);
        //初始化
        mDialogFragment = AbDialogUtil.showLoadDialog(this, R.drawable.ic_load, "正在加载数据。。。");
        mDialogFragment.setAbDialogOnLoadListener(new AbDialogFragment.AbDialogOnLoadListener() {
            @Override
            public void onLoad() {
                load();
            }
        });
        mTabHost.setOnTabChangedListener(new TabChangedListener(mTabHost, mTabWidget));
    }

    private void init() {
        //标题初始化,考虑一下

        viewGroupParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                     ViewGroup.LayoutParams.WRAP_CONTENT);
        linearParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                                     LinearLayout.LayoutParams.WRAP_CONTENT);
        tableParams = new TableLayout.LayoutParams();
        tableRowParams = new TableRow.LayoutParams();

        //轮播图初始化
        playImage();
        //选项卡初始化
        tabTopInit();
        //字体初始化
        fontStyleInit();
        //内容初始化
        int length = CommonUtil.title.length;
        for(int i=0;i<length;i++) {
            initContent(i);
        }
        //下拉刷新监听设置
        for(int i=0;i<CommonUtil.pullToRefresh.length;i++) {
            mPullToRefreshView = (AbPullToRefreshView) findViewById(CommonUtil.pullToRefresh[i]);
            mPullToRefreshView.setOnHeaderRefreshListener(this);
            mPullToRefreshView.setOnFooterLoadListener(this);
            mPullToRefreshView.getHeaderView().setHeaderProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular, null));
            mPullToRefreshView.getFooterView().setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular, null));
        }
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView abPullToRefreshView) {
        refreshTask(abPullToRefreshView, false);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView abPullToRefreshView) {
        loadMoreTask(abPullToRefreshView);
    }

    public void load() {
        AbTask mAbTask = AbTask.newInstance();
        final AbTaskItem item = new AbTaskItem();

        item.setListener(new AbTaskListener() {
            @Override
            public void update() {
                init();
                AbDialogUtil.removeDialog(MagtActivity.this);
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

    public void refreshTask(final AbPullToRefreshView abPullToRefreshView, final boolean isDialog){
        AbTask mAbTask = AbTask.newInstance();
        final AbTaskItem item = new AbTaskItem();
        item.setListener(new AbTaskListener() {
            @Override
            public void update() {
                //刷新页面的代码---重新加载页面
                int index = mTabHost.getCurrentTab()-1;
                tableLayout = (TableLayout) findViewById(CommonUtil.table_id[index]);
                tableLayout.removeAllViews();
                initMangerContent(index);
                if(!isDialog) {
                    abPullToRefreshView.onHeaderRefreshFinish();
                }
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

    public void loadMoreTask(final AbPullToRefreshView abPullToRefreshView){
        AbTask mAbTask = AbTask.newInstance();
        final AbTaskItem item = new AbTaskItem();
        item.setListener(new AbTaskListener() {
            @Override
            public void update() {
                //向上拉的时候显示更多的
                abPullToRefreshView.onFooterLoadFinish();
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

    private final void playImage() {
        for(int i = 0; i< CommonUtil.picIndex; i++) {
            final View mPlayView = mInflater.inflate(R.layout.play_view_item, null);
            ImageView mPlayImage = (ImageView) mPlayView.findViewById(R.id.mPlayImage);
            mPlayImage.setBackgroundResource(CommonUtil.arrPicture[i]);
            mSlidingPlayView.addView(mPlayView);
        }
        mSlidingPlayView.startPlay();
    }

    private final void tabTopInit(){
        //加载页面
        intoDate();
        //设置默认选中页面
        mTabHost.setCurrentTab(0);
        //设置选中样式
        tabInit();
    }

    private void intoDate() {
        labelList = new ArrayList<>();
        pagerList = new ArrayList<>();

        for(String label : CommonUtil.arrTabLabel) {
            labelList.add(label);
        }
        for(Integer pager : CommonUtil.arrTabID) {
            pagerList.add(pager);
        }

        mTabHost.setup();
        for(int i=0;i<CommonUtil.tabIndex;i++) {
            mInflater.inflate(CommonUtil.arrTabContent[i], mTabHost.getTabContentView());
            mTabHost.addTab(mTabHost.newTabSpec(CommonUtil.arrTabTag[i])
                    .setIndicator(labelList.get(i))
                    .setContent(pagerList.get(i)));
        }
    }

    private void tabInit() {
        int count = mTabWidget.getChildCount();

        for(int i=0;i<count;i++) {
            StyleUtil.changeTabColor(mTabHost, mTabWidget, i);
        }
    }

    private final void initContent(int index) {

        switch (index) {
            case 0:
                initInfoContent(R.string.laboratory_profile, 18);
                break;
            case 5:
                initInfoContent(R.string.about_us, 20);
                break;
            default:
                initMangerContent(index-1);
                break;
        }
    }

    /**
     * 初始化信息显示页面
     * @param info
     * @param textSize
     */
    private final void initInfoContent(int info, int textSize) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(viewGroupParams);
        textView.setTextSize(textSize);
        textView.setText(info);
        textView.setLineSpacing(5, 1.5f);
        textView.setGravity(Gravity.LEFT);
        textView.setPadding(StyleUtil.pxToDip(this, 10),
                            StyleUtil.pxToDip(this, 0),
                            StyleUtil.pxToDip(this, 10),
                            StyleUtil.pxToDip(this, 10));
        StyleUtil.fontStyleOnContent(this, textView);
        StyleUtil.fontFakeBold(textView);
        if(info == R.string.laboratory_profile) {
            managerScroll = (ScrollView) findViewById(R.id.main_sv);
            managerScroll.addView(textView);
        } else if(info == R.string.about_us) {
            aboutScroll = (ScrollView) findViewById(R.id.about_sv);
            aboutScroll.addView(textView);
        }
    }

    /**
     * 初始化管理页面
     * @param tag       页面索引
     */
    private final void initMangerContent(int tag) {
        loadTableHead(tag);
    }

    /**
     * 加载表格头
     * @param tag
     */
    private void loadTableHead(int tag) {
        String classPath = CommonUtil.table_type[tag];
        tableLayout = (TableLayout) findViewById(CommonUtil.table_id[tag]);
        if(tag != 3) {
            int length = CommonUtil.table_title[tag].length;
            float width = (StyleUtil.getWindowWidthByPx(this))/CommonUtil.table_splite[tag];
            TextView textView;
            TableRow tableRow = new TableRow(this);
            tableRow.setOrientation(TableRow.HORIZONTAL);
            for(int i=0;i<length;i++) {
                textView = new TextView(this);
                textView.setWidth((int) (width*CommonUtil.table_weight[tag][i]));
                textView.setBackgroundResource(R.drawable.gradient_box);
                AbLogUtil.d(this, String.valueOf(i));
                textView.setGravity(Gravity.CENTER);
                textView.setText(CommonUtil.table_title[tag][i]);
                textView.setSingleLine();
                if(tag == 2 && (i == 3 || i == 4)) {
                    tableLayout.setColumnStretchable(i, true);
                }
                textView.setTextSize(20);
                StyleUtil.fontStyleOnContent(this, textView);
                StyleUtil.fontFakeBold(textView);
                tableRow.addView(textView);
                tableRow.setBackgroundResource(R.drawable.item_no_selected);
            }
            tableLayout.addView(tableRow);
        }
        loadTableContent(tableLayout, tag, classPath);
    }

    /**
     * 加载表格内容
     * @param tag
     */
    private void loadTableContent(TableLayout tableLayout, int tag, String classPath) {
        //获取List列表
        switch (tag) {
            case 0:
                loadEquipmentTable(tableLayout, classPath);
                break;
            case 1:
                loadWarehouseTable(tableLayout, classPath);
                break;
            case 2:
                loadStudentTable(tableLayout, classPath);
                break;
            case 3:
                loadDutyTable(tableLayout, tag, classPath);
                break;
            default:
                AbLogUtil.e(this, "未找到相关信息！");
                break;
        }
    }

    private void loadEquipmentTable(TableLayout tableLayout, String classPath) {
        List<Equipment> equipments = DBUtil.querylist(this, classPath);
        Equipment equipment = null;
        if(equipments != null) {
            int size = equipments.size();
            TextView textView;
            TableRow tableRow;
            for(int i=0;i<size;i++) {
                if(equipments.get(i) != null) {
                    equipment = equipments.get(i);
                    tableRow = new TableRow(this);
                    tableRow.setOrientation(TableRow.HORIZONTAL);

                    textView = new TextView(this);
                    textView.setText(String.valueOf(i+1));
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(equipment.getEqutID());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(equipment.getStatus());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(equipment.getInfo());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);
                    tableRow.setBackgroundResource(R.drawable.item_no_selected);
                    tableLayout.addView(tableRow);
                    tableRow.setOnLongClickListener(new TableItemListener(MagtActivity.this, this, tableLayout, tableRow, CommonUtil.EQUIPMENT_TABLE));
                    tableRow.setOnTouchListener(new TableItemListener(MagtActivity.this, this, tableLayout, tableRow));
                } else {
                    AbToastUtil.showToast(this, "显示数据时出现错误。");
                    break;
                }
            }
        } else {
            AbToastUtil.showToast(this, "当前没有数据显示。");
            return;
        }
    }

    private void loadWarehouseTable(TableLayout tableLayout, String classPath) {
        List<Warehouse> warehouses = DBUtil.querylist(this, classPath);
        Warehouse warehouse = null;
        if(warehouses != null) {
            int size = warehouses.size();
            TextView textView;
            TableRow tableRow;
            for(int i=0;i<size;i++) {
                if(warehouses.get(i) != null) {
                    warehouse = warehouses.get(i);
                    tableRow = new TableRow(this);
                    tableRow.setOrientation(TableRow.HORIZONTAL);

                    textView = new TextView(this);
                    textView.setText(String.valueOf(i+1));
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(warehouse.getType());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(warehouse.getExistNum());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(warehouse.getLendNum());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);
                    tableRow.setBackgroundResource(R.drawable.item_no_selected);
                    tableLayout.addView(tableRow);
                    tableRow.setOnLongClickListener(new TableItemListener(MagtActivity.this,this, tableLayout, tableRow, CommonUtil.WAREHOUSE_TABLE));
                    tableRow.setOnTouchListener(new TableItemListener(MagtActivity.this, this, tableLayout, tableRow));
                } else {
                    AbToastUtil.showToast(this, "显示数据时出现错误。");
                    break;
                }
            }
        } else {
            AbToastUtil.showToast(this, "当前没有数据显示。");
            return;
        }
    }

    private void loadStudentTable(TableLayout tableLayout, String classPath) {
        List<User> users = DBUtil.querylist(this, classPath);
        User user = null;
        if(users != null) {
            int size = users.size();
            TextView textView;
            TableRow tableRow;
            for(int i=0;i<size;i++) {
                if(users.get(i) != null) {
                    user = users.get(i);
                    tableRow = new TableRow(this);
                    tableRow.setOrientation(TableRow.HORIZONTAL);

                    textView = new TextView(this);
                    textView.setText(String.valueOf(i+1));
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(user.getUserName());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(user.getSex());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(user.getClassName());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);

                    textView = new TextView(this);
                    textView.setText(user.getTel());
                    StyleUtil.styleTextView(this, textView);
                    tableRow.addView(textView);
                    tableRow.setBackgroundResource(R.drawable.item_no_selected);
                    tableLayout.addView(tableRow);
                    tableRow.setOnLongClickListener(new TableItemListener(MagtActivity.this, this, tableLayout, tableRow, CommonUtil.USER_TABLE));
                    tableRow.setOnTouchListener(new TableItemListener(MagtActivity.this, this, tableLayout, tableRow));
                } else {
                    AbToastUtil.showToast(this, "显示数据时出现错误。");
                    break;
                }
            }
        } else {
            AbToastUtil.showToast(this, "当前没有数据显示。");
            return;
        }
    }

    private void loadDutyTable(TableLayout tableLayout, int tag, String classPath) {
        List<Duty> names = null;
        TableRow tableRow;
        TextView textView;

        tableRowParams.setMargins(10, 0, 10, 0);
        for(int i=0;i<7;i++) {
            String str = "";
            names = DBUtil.querylist(this, classPath, new String[]{"name"},
                                    "week=?", new String[]{CommonUtil.table_title[tag][i]},
                                    "name", null, null, null);
            tableLayout.setStretchAllColumns(true);
            if(names != null) {
                int size = names.size();
                tableRow = new TableRow(this);
                textView = new TextView(this);
                tableRow.setMinimumHeight(StyleUtil.getWindowHeightByPx(this)/16);
                textView.setText(CommonUtil.table_title[tag][i]);
                textView.setWidth(StyleUtil.getWindowWidthByPx(this)/5);
                StyleUtil.styleTextView(this, textView);
                textView.setTextSize(25);
                StyleUtil.fontFakeBold(textView);
                tableRow.addView(textView, tableRowParams);

                textView = new TextView(this);
                for(int j=0;j<size;j++) {
                    if(j == size-1) {
                        str += names.get(j).getName();
                        break;
                    }
                    str += names.get(j).getName() + "、";
                }
                StyleUtil.styleTextView(this, textView);
                textView.setText(str);
                textView.setTextSize(25);
                textView.setGravity(Gravity.LEFT);
                tableRow.addView(textView, tableRowParams);
                tableLayout.addView(tableRow);
            } else {
                AbToastUtil.showToast(this, "显示数据时出现错误。");
                break;
            }
        }
    }

    private final void fontStyleInit() {
        fontStyleOnTitle();
    }

    /**
     * 选项卡字体设置
     */
    private final void fontStyleOnTitle() {
        //选项卡标签
        for (int i = 0; i< mTabWidget.getChildCount(); i++) {
            TextView textView = mTabWidget.getChildAt(i).findViewById(android.R.id.title);
            textView.setTextSize(16);
            textView.setTextColor(Color.rgb(0, 188, 212));
            StyleUtil.fontStyleOnContent(this, textView);
            StyleUtil.fontFakeBold(textView);
//            AbLogUtil.i("选项卡字体设置：", "设置成功"+i);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(isPause) {
            refreshTask(mPullToRefreshView, false);
            isPause = false;
        }
        AbLogUtil.i("resume====", isPause+"");
    }

    public void onPause() {
        super.onPause();
        isPause = true;
        AbLogUtil.i("Pause====", isPause+"");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        refreshTask(mPullToRefreshView, false);
    }

    @Override
    public void finish() {
        mSlidingPlayView.stopPlay();
        super.finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
