package com.fjnu.labman.dao;

import android.content.Context;

import com.ab.db.orm.dao.AbDBDaoImpl;
import com.fjnu.labman.db.DBHelper;
import com.fjnu.labman.model.Warehouse;

public class WarehouseDao extends AbDBDaoImpl<Warehouse> {
    public WarehouseDao(Context context) {
        super(new DBHelper(context), Warehouse.class);
    }
}
