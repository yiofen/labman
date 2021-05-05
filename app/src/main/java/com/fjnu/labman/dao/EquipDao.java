package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;
import com.fjnu.labman.model.Equipment;

public class EquipDao extends AbDBDaoImpl<Equipment> {
    public EquipDao(Context context) {
        super(new DBHelper(context), Equipment.class);
    }
}
