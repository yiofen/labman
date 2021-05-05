package com.fjnu.labman.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * 库存表
 */
@Table(name = "warehouse")
public class Warehouse {

    @Id
    @Column(name = "_id")
    private int _id;
    @Column(name = "type", length = 10)
    private String type;
    @Column(name = "exist_number", length = 5)
    private String existNum;
    @Column(name = "lend_number", length = 5)
    private String lendNum;

    public Warehouse() {
    }

    public Warehouse(String type, String existNum, String lendNum) {
        this.type = type;
        this.existNum = existNum;
        this.lendNum = lendNum;
    }

    public Warehouse(int _id, String type, String existNum, String lendNum) {
        this._id = _id;
        this.type = type;
        this.existNum = existNum;
        this.lendNum = lendNum;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExistNum() {
        return existNum;
    }

    public void setExistNum(String existNum) {
        this.existNum = existNum;
    }

    public String getLendNum() {
        return lendNum;
    }

    public void setLendNum(String lendNum) {
        this.lendNum = lendNum;
    }
}
