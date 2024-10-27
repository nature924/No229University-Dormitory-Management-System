package com.usc.lzh.doms.vo;

import com.usc.lzh.doms.entity.BuildRoomInfo;

public class BuildRoomInfoVo extends BuildRoomInfo {
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
        return "BuildRoomInfoVo{" +
                "page=" + page +
                ", limit=" + limit +
                super.toString() +
                '}';
    }
}
