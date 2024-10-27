package com.usc.lzh.doms.utils;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {

    public static List<MenuNode> toTree(List<MenuNode> treeList, Integer mPid) {
        List<MenuNode> retList = new ArrayList<MenuNode>();
        for (MenuNode parent : treeList) {
            if (mPid.equals(parent.getPid())) {
                retList.add(findChildren(parent, treeList));
            }
        }
        return retList;
    }
    private static MenuNode findChildren(MenuNode parent, List<MenuNode> treeList) {
        for (MenuNode child : treeList) {
            if (parent.getId().equals(child.getPid())) {
                if (parent.getChild() == null) {
                    parent.setChild(new ArrayList<>());
                }
                parent.getChild().add(findChildren(child, treeList));
            }
        }
        return parent;
    }
}
