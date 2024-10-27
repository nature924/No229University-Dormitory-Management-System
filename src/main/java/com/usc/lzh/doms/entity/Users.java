package com.usc.lzh.doms.entity;

public class Users {
    private String uid;
    private String uname;
    private String upwd;
    private Integer utype;
    // 教师负责的专业
    private String dept;
    // 教师负责的年级
    private String grade;
    // 宿管员负责的宿舍区
    private String brarea;
    // 宿管员负责的宿舍楼
    private String brbid;
    private String utel;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public Integer getUtype() {
        return utype;
    }

    public void setUtype(Integer utype) {
        this.utype = utype;
    }

    public String getUtel() {
        return utel;
    }

    public void setUtel(String utel) {
        this.utel = utel;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getBrarea() {
        return brarea;
    }

    public void setBrarea(String brarea) {
        this.brarea = brarea;
    }

    public String getBrbid() {
        return brbid;
    }

    public void setBrbid(String brbid) {
        this.brbid = brbid;
    }

    @Override
    public String toString() {
        return "Users{" +
                "uid='" + uid + '\'' +
                ", uname='" + uname + '\'' +
                ", upwd='" + upwd + '\'' +
                ", utype=" + utype +
                ", dept='" + dept + '\'' +
                ", grade='" + grade + '\'' +
                ", brarea='" + brarea + '\'' +
                ", brbid='" + brbid + '\'' +
                ", utel='" + utel + '\'' +
                '}';
    }
}
