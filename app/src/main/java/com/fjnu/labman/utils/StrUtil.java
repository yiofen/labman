package com.fjnu.labman.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.ab.util.AbStrUtil;
import com.ab.util.AbToastUtil;
import com.fjnu.labman.R;

/**
 * 字符串输入规范检查
 */
public class StrUtil extends AbStrUtil {

    /**
     * 检查ID/账号规范
     * @param context
     * @param view
     * @param userID
     * @return
     */
    public static boolean checkID(Context context, View view, String userID) {
        if(TextUtils.isEmpty(userID)) {
            AbToastUtil.showToast(context, R.string.error_name);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        if(!AbStrUtil.isNumberLetter(userID)) {
            AbToastUtil.showToast(context, R.string.error_name_expr);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        if(AbStrUtil.strLength(userID)<6) {
            AbToastUtil.showToast(context, R.string.error_name_length1);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        if(AbStrUtil.strLength(userID)>16) {
            AbToastUtil.showToast(context, R.string.error_name_length2);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 检测密码规范
     * @param context
     * @param view
     * @param pwd
     * @return
     */
    public static boolean checkPwd(Context context, View view, String pwd) {
        if(AbStrUtil.isEmpty(pwd)) {
            AbToastUtil.showToast(context, R.string.error_pwd);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        if(AbStrUtil.strLength(pwd)<6) {
            AbToastUtil.showToast(context, R.string.error_pwd_length1);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        if(AbStrUtil.strLength(pwd)>20) {
            AbToastUtil.showToast(context, R.string.error_pwd_length2);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 检查确认密码规范
     * @param context
     * @param view
     * @param pwd
     * @param pwdConf
     * @return
     */
    public static boolean checkPwdConf(Context context, View view, String pwd, String pwdConf) {
        if(AbStrUtil.isEmpty(pwdConf)) {
            AbToastUtil.showToast(context, R.string.error_pwd_conf);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        if(!pwdConf.equals(pwd)) {
            AbToastUtil.showToast(context, R.string.error_pwd_match);
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 检查输入信息规范
     * @param context
     * @param view
     * @param strs
     * @return
     */
    public static boolean checkInfo(Context context, View[] view, String... strs) {
        int index = 0;
        for(String str : strs) {
            if(AbStrUtil.isEmpty(str)) {
                AbToastUtil.showToast(context, R.string.error_info_complete);
                view[index].setFocusable(true);
                view[index].requestFocus();
                return false;
            }
            index++;
        }

        return true;
    }

}
