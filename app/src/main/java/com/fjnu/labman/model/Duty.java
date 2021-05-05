package com.fjnu.labman.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * 卫生安排表
 */
@Table(name = "duty")
public class Duty {
    @Id
    @Column(name = "_id")
    private int _id;
    @Column(name = "week")
    private String week;//星期数(Sun, Mon, Tue, Wed, Thu, Fri, Sat)
    @Column(name = "name", length = 10)
    private String name;

    public Duty() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
