package com.fjnu.labman.db;

import android.content.Context;

import com.ab.db.orm.AbDBHelper;
import com.fjnu.labman.model.Course;
import com.fjnu.labman.model.Duty;
import com.fjnu.labman.model.Equipment;
import com.fjnu.labman.model.Schedule;
import com.fjnu.labman.model.User;
import com.fjnu.labman.model.Warehouse;

public class DBHelper extends AbDBHelper {
	//数据库名
	private static final String DBNAME = "labman.db";
    //当前数据库的版本
	private static final int DBVERSION = 1;
	//要初始化的表
	private static final Class<?>[] clazz = {
			User.class, Equipment.class, Warehouse.class,
			Course.class, Schedule.class, Duty.class
	};

	public DBHelper(Context context) {
		super(context, DBNAME, null, DBVERSION, clazz);
	}

}



