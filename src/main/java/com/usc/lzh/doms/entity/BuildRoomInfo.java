package com.usc.lzh.doms.entity;

import com.usc.lzh.doms.utils.ExcelColumn;

/**
 * @author:lzh
 * @time:2021.4.9
 * 宿舍楼栋信息
 */
public class BuildRoomInfo {
    private String brcode;

    @ExcelColumn(value = "宿舍区",col = 1)
    private String brarea;

    @ExcelColumn(value = "楼栋",col = 2)
    private String brbid;

    @ExcelColumn(value = "寝室",col = 3)
    private String brrid;

    @ExcelColumn(value = "性质",col = 4)
    private String sex;

    @ExcelColumn(value = "床位数",col = 5)
    private Integer volume;

    @ExcelColumn(value = "入住人数",col = 6)
    private Integer people;

    @ExcelColumn(value = "空余数",col = 7)
    private Integer free;

    public BuildRoomInfo(){

    }

    public BuildRoomInfo(String brcode, int free) {
        this.free = free;
        this.brcode = brcode;
    }

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

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getFree() {
        return free;
    }

    public void setFree(Integer free) {
        this.free = free;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    @Override
    public String toString() {
        return "BuildRoomInfo{" +
                "brcode='" + brcode + '\'' +
                ", brarea='" + brarea + '\'' +
                ", brbid='" + brbid + '\'' +
                ", brrid='" + brrid + '\'' +
                ", volume=" + volume +
                ", sex='" + sex + '\'' +
                ", free=" + free +
                ", people=" + people +
                '}';
    }
}
