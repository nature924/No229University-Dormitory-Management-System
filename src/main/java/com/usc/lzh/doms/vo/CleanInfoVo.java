package com.usc.lzh.doms.vo;

import com.usc.lzh.doms.entity.CleanInfo;
import com.usc.lzh.doms.utils.ExcelColumn;

public class CleanInfoVo extends CleanInfo {
    private Integer page;
    private Integer limit;
    private String stucollege;
    private String studept;
    private String stuclass;
    private String stuid;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
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

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    @Override
    public String toString() {
        return "CleanInfoVo{" +
                "page=" + page +
                ", limit=" + limit +
                ", stucollege='" + stucollege + '\'' +
                ", studept='" + studept + '\'' +
                ", stuclass='" + stuclass + '\'' +
                ", stuid='" + stuid + '\'' +
                "," + super.toString() +
                '}';
    }
}
