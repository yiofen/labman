package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;
import com.fjnu.labman.model.Duty;

public class DutyDao extends AbDBDaoImpl<Duty> {
    public DutyDao(Context context) {
        super(new DBHelper(context), Duty.class);
    }

}
