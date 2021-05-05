package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;

public class ClassDao<T> extends AbDBDaoImpl<T> {

    public ClassDao(Context context, Class<T> clazz) {
        super(new DBHelper(context), clazz);
    }
}
