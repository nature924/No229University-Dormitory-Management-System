package com.usc.lzh.doms.vo;

import com.usc.lzh.doms.entity.MessageBoard;

public class MessageBoardVo extends MessageBoard {
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
        return "MessageBoardVo{" +
                "page=" + page +
                ", limit=" + limit +
                ", " + super.toString() +
                '}';
    }
}
