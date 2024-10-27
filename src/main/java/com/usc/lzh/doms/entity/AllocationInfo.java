package com.usc.lzh.doms.entity;

import com.usc.lzh.doms.utils.ExcelColumn;

/**
 * @author:lzh
 * @time:2021.4.10 宿舍分配/预分配信息
 */
public class AllocationInfo {
    // 学生相关信息
    @ExcelColumn(value = "学号", col = 1)
    private String stuid;

    @ExcelColumn(value = "姓名", col = 2)
    private String stuname;

    @ExcelColumn(value = "学院", col = 3)
    private String stucollege;

    @ExcelColumn(value = "专业", col = 4)
    private String studept;

    @ExcelColumn(value = "班级", col = 5)
    private String stuclass;

    // 宿舍相关信息
    @ExcelColumn(value = "宿舍编号", col = 6)
    private String brcode;

    @ExcelColumn(value = "宿舍区", col = 7)
    private String brarea;

    @ExcelColumn(value = "楼栋", col = 8)
    private String brbid;

    @ExcelColumn(value = "寝室", col = 9)
    private String brrid;

    @ExcelColumn(value = "床位号", col = 10)
    private Integer bed;
    // 预分配的状态（1已选床位/0未选）
    private Integer status;

    private String stugrade;

    private String stutel;

    public String getBrcode() {
        return brcode;
    }

    public void setBrcode(String brcode) {
        this.brcode = brcode;
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

    public String getBrrid() {
        return brrid;
    }

    public void setBrrid(String brrid) {
        this.brrid = brrid;
    }

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

    public Integer getBed() {
        return bed;
    }

    public void setBed(Integer bed) {
        this.bed = bed;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    @Override
    public String toString() {
        return "AllocationInfo{" +
                "stuid='" + stuid + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stucollege='" + stucollege + '\'' +
                ", studept='" + studept + '\'' +
                ", stuclass='" + stuclass + '\'' +
                ", brcode='" + brcode + '\'' +
                ", brarea='" + brarea + '\'' +
                ", brbid='" + brbid + '\'' +
                ", brrid='" + brrid + '\'' +
                ", bed=" + bed +
                ", status=" + status +
                ", stugrade='" + stugrade + '\'' +
                '}';
    }
}
