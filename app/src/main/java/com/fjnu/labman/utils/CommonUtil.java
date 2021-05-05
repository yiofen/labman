package com.fjnu.labman.utils;

import android.graphics.Color;

import com.fjnu.labman.R;
import com.fjnu.labman.model.Duty;
import com.fjnu.labman.model.Equipment;
import com.fjnu.labman.model.User;
import com.fjnu.labman.model.Warehouse;

/**
 * 静态字符串或数组
 */
public class CommonUtil {
    //request code
    public static final int TO_REGISTE = 0x02;

    //gain code
    public static final String RETURN_ID = "return_id";
    public static final int EQUIPMENT_TABLE = 0;
    public static final int WAREHOUSE_TABLE = 1;
    public static final int USER_TABLE = 2;
    public static final int DUTY_TABLE = 3;

    //week
    public static final String SUNDAY = "Sun.";
    public static final String MONDAY = "Mon.";
    public static final String TUESDAY = "Tues.";
    public static final String WEDNESDAY = "Wed.";
    public static final String THURSDAY = "Thur.";
    public static final String FRIDAY = "Fri.";
    public static final String SATURDAY = "Sat.";

    public static final String[] weeks = {
    };

    //classpath-----relative path
    public static final String USER = "com.fjnu.labman.model.User";
    public static final String EQUIPMENT = "com.fjnu.labman.model.Equipment";
    public static final String WAREHOUSE = "com.fjnu.labman.model.Warehouse";
    public static final String DUTY = "com.fjnu.labman.model.Duty";
    public static final String COURSE = "com.fjnu.labman.model.Course";
    public static final String SCHEDULE = "com.fjnu.labman.model.Schedule";

    //class index
    public static final int EQUIPMENT_INDEX = 0;
    public static final int WAREHOUSE_INDEX = 1;
    public static final int USER_INDEX = 2;
    public static final int DUTY_INDEX = 3;
    public static final int COURSE_INDEX = 4;
    public static final int SCHEDULE_INDEX = 5;

    //字体
    public static final String KAITI_GB2312 = "fonts/kaiti_gb2312.ttf";
    public static final String FANGSONG_GB2312 = "fonts/fangsong_gb2312.ttf";

    //颜色
    public static final int WHITE = Color.WHITE;
    public static final int BLACK = Color.BLACK;

    //轮播图片
    public final static int[] arrPicture = new int[]{
            R.drawable.lab01, R.drawable.lab02
    };

    //选项卡
    public final static int[] arrTabContent = new int[]{
            R.layout.magt_main, R.layout.magt_equt, R.layout.magt_ware,
            R.layout.magt_stud, R.layout.magt_duty, R.layout.about_us
    };
    public final static int[] arrTabID = new int[]{
            R.id.magt_main, R.id.magt_equt, R.id.magt_ware,
            R.id.magt_stud, R.id.magt_duty, R.id.magt_about_us
    };
    public final static String[] arrTabTag = new String[]{
            "main", "equt", "ware", "stud", "duty", "about",
    };
    public final static String[] arrTabLabel = new String[]{
            "主页", "设备", "仓库", "学生", "卫生", "关于",
    };
    public static int picIndex = arrPicture.length;
    public static int tabIndex = arrTabContent.length;

    //主页文本框


    //Tab 内容
    public static final int[] title = {
            R.string.laboratory_main_title, R.string.laboratory_equt_title,
            R.string.laboratory_warehouse_title, R.string.laboratory_student_title,
            R.string.laboratory_duty_title, R.string.laboratory_about_us_title
    };
    public static final String[] table_type = {
            EQUIPMENT, WAREHOUSE,
            USER, DUTY,
//            COURSE, SCHEDULE
    };
    public static final Class[] table_class = {
            Equipment.class, Warehouse.class,
            User.class, Duty.class
    };
    public static final int[] table_id = {
            R.id.equt_table, R.id.ware_table,
            R.id.stud_table, R.id.duty_table
    };
    public static final String[][] table_title = {
            {"序号", "设备号", "状态", "备注"},
            {"序号", "种类", "剩余数量", "借出数量"},
            {"序号", "姓名", "性别", "班级", "电话"},
            {SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY}
    };
    public static final float[][] table_weight = {
            {1.0f, 2.0f, 1.0f, 2.0f},
            {1.0f, 1.5f, 2.0f, 2.0f},
            {1.0f, 1.5f, 1.0f, 2.0f, 2.0f},
            {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f}
    };
    public static final float[] table_splite = {
            6, 6.5f, 7.5f, 7
    };

    public static final int[] pullToRefresh = {
            R.id.mPullRefreshEqut, R.id.mPullRefreshWare,
            R.id.mPullRefreshStud, R.id.mPullRefreshSuty
    };



}
