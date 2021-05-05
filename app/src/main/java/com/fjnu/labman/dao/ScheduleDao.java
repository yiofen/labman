package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;
import com.fjnu.labman.model.Schedule;

public class ScheduleDao extends AbDBDaoImpl<Schedule> {
    public ScheduleDao(Context context) {
        super(new DBHelper(context), Schedule.class);
    }


}
