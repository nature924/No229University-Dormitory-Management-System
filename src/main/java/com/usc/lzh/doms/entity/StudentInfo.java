package com.usc.lzh.doms.entity;

import com.usc.lzh.doms.utils.ExcelColumn;

/**
 * @author:lzh
 * @time:2021.4.9
 * 学生基本信息
 */
public class StudentInfo {
    @ExcelColumn(value = "学号",col = 1)
    private String stuid;

    @ExcelColumn(value = "姓名",col = 2)
    private String stuname;

    @ExcelColumn(value = "性别",col = 3)
    private String stusex;

    @ExcelColumn(value = "学院",col = 4)
    private String stucollege;

    @ExcelColumn(value = "专业",col = 5)
    private String studept;

    @ExcelColumn(value = "班级",col = 6)
    private String stuclass;

    private String stugrade;

    @ExcelColumn(value = "手机号",col = 7)
    private String stutel;

    @ExcelColumn(value = "QQ",col = 8)
    private String stuqq;

    @ExcelColumn(value = "微信号",col = 9)
    private String stuwx;

    @ExcelColumn(value = "监护人姓名",col = 10)
    private String stututorname;

    @ExcelColumn(value = "监护人联系方式",col = 11)
    private String stututortel;

    @ExcelColumn(value = "家庭住址",col = 12)
    private String stuaddress;

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getStusex() {
        return stusex;
    }

    public void setStusex(String stusex) {
        this.stusex = stusex;
    }

    public String getStucollege() {
        return stucollege;
    }

    public void setStucollege(String stucollege) {
        this.stucollege = stucollege;
    }

    public String getStudept() {
        return studept;
    }

    public void setStudept(String studept) {
        this.studept = studept;
    }

    public String getStuclass() {
        return stuclass;
    }

    public void setStuclass(String stuclass) {
        this.stuclass = stuclass;
    }

    public String getStugrade() {
        return stugrade;
    }

    public void setStugrade(String stugrade) {
        this.stugrade = stugrade;
    }

    public String getStutel() {
        return stutel;
    }

    public void setStutel(String stutel) {
        this.stutel = stutel;
    }

    public String getStuqq() {
        return stuqq;
    }

    public void setStuqq(String stuqq) {
        this.stuqq = stuqq;
    }

    public String getStuwx() {
        return stuwx;
    }

    public void setStuwx(String stuwx) {
        this.stuwx = stuwx;
    }

    public String getStututorname() {
        return stututorname;
    }

    public void setStututorname(String stututorname) {
        this.stututorname = stututorname;
    }

    public String getStututortel() {
        return stututortel;
    }

    public void setStututortel(String stututortel) {
        this.stututortel = stututortel;
    }

    public String getStuaddress() {
        return stuaddress;
    }

    public void setStuaddress(String stuaddress) {
        this.stuaddress = stuaddress;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "stuid='" + stuid + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stusex='" + stusex + '\'' +
                ", stucollege='" + stucollege + '\'' +
                ", studept='" + studept + '\'' +
                ", stuclass='" + stuclass + '\'' +
                ", stugrade='" + stugrade + '\'' +
                ", stutel='" + stutel + '\'' +
                ", stuqq='" + stuqq + '\'' +
                ", stuwx='" + stuwx + '\'' +
                ", stututorname='" + stututorname + '\'' +
                ", stututortel='" + stututortel + '\'' +
                ", stuaddress='" + stuaddress + '\'' +
                '}';
    }
}
