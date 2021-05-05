package com.fjnu.labman.utils;

import android.content.Context;
import android.view.View;

import com.ab.util.AbToastUtil;
import com.fjnu.labman.R;
import com.fjnu.labman.dao.ClassDao;
import com.fjnu.labman.model.Duty;
import com.fjnu.labman.model.User;

import java.util.List;

public class DBUtil {

    /**
     * 登录校验
     * @param u_id
     * @param pwd
     * @return
     */
    public static boolean loginSuccess(Context context, View[] views, String u_id, String pwd) {
        ClassDao classDao = new ClassDao(context, User.class);
        //首先判断账号是否存在，确定账号是否输入错误
        String sql1 = "select * from user where u_id=?";
        //之后进行账号密码校验，确定密码是否输入错误
        String sql2 = "select * from user where u_id=? and password=?";
        classDao.startReadableDatabase();//打开数据库,设置可读
        if(!classDao.isExist(sql1, new String[]{u_id})) {
            classDao.closeDatabase();
            AbToastUtil.showToast(context, R.string.error_id_input);
            views[0].setFocusable(true);
            views[0].requestFocus();
            return false;
        }
        if(!classDao.isExist(sql2, new String[]{u_id, pwd})) {
            classDao.closeDatabase();
            AbToastUtil.showToast(context, R.string.error_pwd_input);
            views[1].setFocusable(true);
            views[1].requestFocus();
            return false;
        }
        classDao.closeDatabase();
        return true;
    }

    /**
     * 注册校验
     * @param context
     * @param view
     * @param user
     * @param u_id
     * @return
     */
    public static boolean registerSuccess(Context context, View view, User user, String u_id) {
        ClassDao classDao = new ClassDao(context, User.class);
        //数据库匹配查看是否已存在当前帐号
        String sql = "select * from user where u_id=?";
        classDao.startReadableDatabase();//要记得打开数据库---此处为仅可读
        if(classDao.isExist(sql, new String[]{u_id})) {
            AbToastUtil.showToast(context, R.string.error_name_exist);
            classDao.closeDatabase();
            view.setFocusable(true);
            view.requestFocus();
            return false;
        }
        classDao.startWritableDatabase(true);//要记得打开数据库---此处为可写
        classDao.insert(user);
        classDao.closeDatabase();
        return true;
    }

    /**
     * 查询数据列表
     * @param context
     * @param classPath
     * @return
     */
    public static List querylist(Context context, String classPath) {
        return querylist(context, classPath, null, null,
                null, null, null, null, null);
    }

    /**
     * 根据条件查询数据列表
     * @param context
     * @param classPath
     * @param columns
     * @param selection
     * @param selectionArgs
     * @param groupBy
     * @param having
     * @param orderBy
     * @param limit
     * @return
     */
    public static List querylist(Context context, String classPath, String[] columns,
                                 String selection, String[] selectionArgs, String groupBy,
                                 String having, String orderBy, String limit) {
        List list;
        Class clazz;
        ClassDao classDao;
        try {
            clazz = Class.forName(classPath);
            classDao = new ClassDao(context, clazz);
            classDao.startReadableDatabase();
            list = classDao.queryList(columns, selection, selectionArgs,
                                        groupBy, having, orderBy, limit);
            classDao.closeDatabase();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     *
     * @param context
     * @param clazz
     * @param object
     * @return
     */
    public static boolean insert(Context context, Class clazz, Object object) {
        if(object == null) {
            return false;
        }
        ClassDao classDao = new ClassDao(context, clazz);
        classDao.startWritableDatabase(true);
        classDao.insert(object);
        classDao.closeDatabase();
        return true;
    }

    /**
     *
     * @param context
     * @param classPath
     * @param object
     * @return
     */
    public static boolean update(Context context, String classPath, Object object) {
        Class clazz;
        try {
            clazz = Class.forName(classPath);
            ClassDao classDao = new ClassDao(context, clazz);
            classDao.startWritableDatabase(true);
            classDao.update(object);
            classDao.closeDatabase();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除数据
     * @param context
     * @param clazz
     * @param id
     * @return
     */
    public static boolean delete(Context context, Class clazz, int id) {
        ClassDao classDao = new ClassDao(context, clazz);
        classDao.startWritableDatabase(true);
        classDao.delete(id);
        classDao.closeDatabase();
        return true;
    }

}
