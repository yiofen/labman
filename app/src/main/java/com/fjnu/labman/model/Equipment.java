package com.fjnu.labman.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * 设备状态表
 */
@Table(name = "equipment")
public class Equipment {

    @Id
    @Column(name = "_id")
    private int _id;
    @Column(name = "equt_id", length = 10)
    private String equtID;
    @Column(name = "status", length = 5)
    private String status;
    @Column(name = "info", length = 50)
    private String info;

    public Equipment() {
    }

    public Equipment(String equtID, String status, String info) {
        this.equtID = equtID;
        this.status = status;
        this.info = info;
    }

    public Equipment(int _id, String equtID, String status, String info) {
        this._id = _id;
        this.equtID = equtID;
        this.status = status;
        this.info = info;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getEqutID() {
        return equtID;
    }

    public void setEqutID(String equtID) {
        this.equtID = equtID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
