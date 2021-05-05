package com.fjnu.labman.model;

import com.ab.db.orm.annotation.Column;
import com.ab.db.orm.annotation.Id;
import com.ab.db.orm.annotation.Table;

/**
 * 用户表
 */
@Table(name = "user")
public class User {

    @Id
    @Column(name = "_id")
    private int _id;
    @Column(name = "u_id", length = 16)
    private String u_id;//登录账号
    @Column(name = "uname", length = 10)
    private String userName;//姓名
    @Column(name = "password", length = 20)
    private String pwd;
    @Column(name = "tel", length = 11)
    private String Tel;
    @Column(name = "sex", length = 2)
    private String sex;
    @Column(name = "class_name", length = 20)
    private String className;
    @Column(name = "age", type = "INTEGER", length = 3)
    private Integer age;

    public User() {
    }

    public User(String u_id, String userName, String pwd, String tel, String sex, String className, Integer age) {
        this.u_id = u_id;
        this.userName = userName;
        this.pwd = pwd;
        Tel = tel;
        this.sex = sex;
        this.className = className;
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getTel() {
        return Tel;
    }

    public void setTel(String tel) {
        Tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
