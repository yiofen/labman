package com.fjnu.labman.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * 课程目录表
 */
@Table(name = "course")
public class Course {
    @Id
    @Column(name = "_id")
    private int _id;
    @Column(name = "subject")
    private String subject;

    public Course() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subjects) {
        this.subject = subjects;
    }
}
