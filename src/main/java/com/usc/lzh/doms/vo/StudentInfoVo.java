package com.usc.lzh.doms.vo;

import com.usc.lzh.doms.entity.StudentInfo;

public class StudentInfoVo extends StudentInfo {
    private Integer page;
    private Integer limit;

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

    @Override
    public String toString() {
        return "StudentInfoVo{" +
                "page=" + page +
                ", limit=" + limit +
                ", stuid=" + getStuid() +
                ", stuname=" + getStuname() +
                ", stuclass=" + getStuclass() +
                '}';
    }
}
