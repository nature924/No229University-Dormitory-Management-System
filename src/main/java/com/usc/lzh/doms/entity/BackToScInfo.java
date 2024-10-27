package com.usc.lzh.doms.entity;

import com.usc.lzh.doms.utils.ExcelColumn;

/**
 * 返校信息
 */
public class BackToScInfo {
    private Integer id;
    @ExcelColumn(value = "学号", col = 1)
    private String stuid;

    @ExcelColumn(value = "是否延迟返校", col = 7)
    private String delay;

    @ExcelColumn(value = "延迟原因", col = 8)
    private String reason;

    @ExcelColumn(value = "返校时间", col = 9)
    private String time;

    @ExcelColumn(value = "返校方式", col = 10)
    private String way;

    @ExcelColumn(value = "车次信息", col = 11)
    private String trainnum;

    @ExcelColumn(value = "姓名", col = 2)
    private String stuname;

    @ExcelColumn(value = "学院", col = 3)
    private String stucollege;

    @ExcelColumn(value = "专业", col = 4)
    private String studept;

    @ExcelColumn(value = "班级", col = 5)
    private String stuclass;

    private String stugrade;

    @ExcelColumn(value = "联系方式", col = 6)
    private String stutel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public String getTrainnum() {
        return trainnum;
    }

    public void setTrainnum(String trainnum) {
        this.trainnum = trainnum;
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
        return "BackToSCInfo{" +
                "id=" + id +
                ", stuid='" + stuid + '\'' +
                ", delay='" + delay + '\'' +
                ", reason='" + reason + '\'' +
                ", time='" + time + '\'' +
                ", way='" + way + '\'' +
                ", trainnum='" + trainnum + '\'' +
                ", stuname='" + stuname + '\'' +
                ", stucollege='" + stucollege + '\'' +
                ", studept='" + studept + '\'' +
                ", stuclass='" + stuclass + '\'' +
                ", stugrade='" + stugrade + '\'' +
                ", stutel='" + stutel + '\'' +
                '}';
    }
}
