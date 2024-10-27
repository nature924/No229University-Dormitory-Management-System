package com.usc.lzh.doms.utils;

import java.util.List;

public class MenuNode {
    private Integer id;
    private Integer pid;
    private String title;
    private String href;
    private String target;
    private String icon;
    private String utype;
    private List<MenuNode> child;//子菜单列表

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<MenuNode> getChild() {
        return child;
    }

    public void setChild(List<MenuNode> child) {
        this.child = child;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    @Override
    public String toString() {
        return "MenuNode{" +
                "id=" + id +
                ", pid=" + pid +
                ", title='" + title + '\'' +
                ", href='" + href + '\'' +
                ", target='" + target + '\'' +
                ", icon='" + icon + '\'' +
                ", utype='" + utype + '\'' +
                ", child=" + child +
                '}';
    }
}
