package com.fjnu.labman.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.fjnu.labman.R;

import java.util.ArrayList;
import java.util.List;

public class StyleUtil {
    public static List<View> views = new ArrayList<>();
    public static Display display = null;
    public static DisplayMetrics displayMetrics = null;
    public static TextPaint textPaint;

    /**
     * 获取所有控件
     * @return
     */
    public static void getAllChildViews(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();
        if(count != 0) {
            for(int i=0;i<count;i++) {
                getAllChildViews(viewGroup);
                View view = viewGroup.getChildAt(i);
                if(view instanceof TextView) {
                    views.add(view);
                }
            }
        } else {
            return;
        }
    }

    /**
     * px转换为dip
     * @param context
     * @param px
     * @return
     */
    public static int pxToDip(Context context, int px) {
        int dip = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                px, context.getResources().getDisplayMetrics());
        return dip;
    }

    /**
     * 获取屏幕高度---px像素
     * @param activity
     * @return
     */
    public static int getWindowHeightByPx(Activity activity) {
        displayMetrics = activity.getResources().getDisplayMetrics();
        int heightPx = displayMetrics.heightPixels;
        return heightPx;
    }

    /**
     * 获取屏幕高度---dip
     * @param activity
     * @param context
     * @return
     */
    public static int getWindowHeightByDip(Activity activity, Context context) {
        int heightDip = pxToDip(context, getWindowHeightByPx(activity));
        return heightDip;
    }

    /**
     * 获取屏幕宽度---px像素
     * @param activity
     * @return
     */
    public static int getWindowWidthByPx(Activity activity) {
        displayMetrics = activity.getResources().getDisplayMetrics();
        int widthPx = displayMetrics.widthPixels;
        return widthPx;
    }

    /**
     * 获取屏幕宽度---dip
     * @param activity
     * @param context
     * @return
     */
    public static int getWindowWidthByDip(Activity activity, Context context) {
        int widthDip = pxToDip(context, getWindowWidthByPx(activity));
        return widthDip;
    }


    /**
     * 文本框字体样式
     * @param textView
     */
    public static void fontStyleOnContent(Context context, TextView textView) {
        //对每个文本框进行字体修改
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(),
                CommonUtil.KAITI_GB2312),
                Typeface.NORMAL);
    }

    /**
     * 字体加粗
     * @param textView
     */
    public static void fontFakeBold(TextView textView) {
        textPaint = textView.getPaint();
        textPaint.setFakeBoldText(true);
    }

    /**
     * 设置文本框公共样式
     * @param context
     * @param textView
     */
    public static void styleTextView(Context context, TextView textView) {
        textView.setBackgroundResource(R.drawable.gradient_box);
        textView.setGravity(Gravity.CENTER);
        textView.setSingleLine();
        textView.setTextSize(20);
        fontStyleOnContent(context, textView);
    }

    /**
     * Tab标签选中样式
     * @param position
     */
    public static void changeTabColor(TabHost tabHost, TabWidget tabWidget, int position) {
        //选中样式
        View view = tabWidget.getChildAt(position);
        if(position == tabHost.getCurrentTab()) {
            view.setBackgroundResource(R.drawable.tab_selected);
        } else {
            view.setBackgroundResource(R.drawable.tab_no_selected);
        }
    }

    /**
     * 表格项选中样式
     * @param tableLayout
     * @param tableRow
     * @param position
     */
    public static void changeItemColor(TableLayout tableLayout, TableRow tableRow, int position) {
         View view = tableLayout.getChildAt(position);
         if(position == tableLayout.indexOfChild(tableRow)) {
             view.setBackgroundResource(R.drawable.item_selected);
         } else {
             view.setBackgroundResource(R.drawable.item_no_selected);
         }
    }
}
