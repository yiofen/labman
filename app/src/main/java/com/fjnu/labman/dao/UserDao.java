package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;
import com.fjnu.labman.model.User;

public class UserDao extends AbDBDaoImpl<User> {
    public UserDao(Context context) {
        super(new DBHelper(context), User.class);
    }
}
