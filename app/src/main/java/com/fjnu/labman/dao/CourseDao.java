package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;
import com.fjnu.labman.model.Course;

public class CourseDao extends AbDBDaoImpl<Course> {
    public CourseDao(Context context) {
        super(new DBHelper(context), Course.class);
    }
}
