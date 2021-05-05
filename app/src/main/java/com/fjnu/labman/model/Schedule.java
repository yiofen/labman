package com.fjnu.labman.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Relations;
import com.ab.db.orm.annotation.Table;

import java.util.List;

/**
 * 学生课程表
 */
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(name = "_id")
    private int _id;
    @Column(name = "time", length = 2)
    private String time;//两个数字字符,前者表示星期几,后者表示第几节课,一天最多六节课
    @Relations(name = "users", foreignKey = "_id", type = "many2many", action = "query_insert")
    private List<User> users;
    @Relations(name = "courses", foreignKey = "c_id", type = "many2many", action = "query_insert")
    private List<Course> courses;

    public Schedule() {
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
