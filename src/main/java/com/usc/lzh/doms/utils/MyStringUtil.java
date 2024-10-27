package com.usc.lzh.doms.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyStringUtil {
    private static Map<String, String> brMap = new HashMap<String, String>() {{
        put("qsy", "求是园");
        put("mdy", "明德园");
        put("bxy", "博学园");
        put("zyy", "致远园");
        put("dxy", "笃行园");
    }};

    private static Map<Integer, String> mbMap = new HashMap<Integer, String>() {{
        put(0, "公告");
        put(1, "失物招领");
        put(2, "普通留言");
    }};

    private static Map<Integer, String> leaveMap = new HashMap<Integer, String>() {{
        put(0, "留校");
        put(1, "回家");
        put(2, "外出");
    }};

    private static Map<Integer, String> stayinMap = new HashMap<Integer, String>() {{
        put(0, "不批准");
        put(1, "批准");
    }};

    public static String getBrarea(String brarea) {
        return brMap.get(brarea);
    }

    public static String getBrcode(String area, String bid, String rid) {
        StringBuffer buffer = new StringBuffer();
        if (StringUtils.isNotBlank(area)) {
            for (Map.Entry<String, String> entry : brMap.entrySet()) {
                if (entry.getValue().equals(area.trim())) {
                    buffer.append(entry.getKey());
                }
            }
        }
        if (StringUtils.isNotBlank(bid)) {
            buffer.append("#");
            buffer.append(bid.trim());
        }
        if (StringUtils.isNotBlank(rid)) {
            buffer.append("#");
            buffer.append(rid.trim());
        }
        return buffer.toString();
    }

    public static String timeTodate(String time) {
        // 如果time不空且符合格式，返回日期回去
        if (StringUtils.isNotBlank(time)) {
            int spaceIndex = time.indexOf(" ");
            if (spaceIndex != -1) {
                String date = time.substring(0, spaceIndex);
                return date;
            }
        }
        return time;
    }

    /**
     * 根据公告的key获取类型值
     *
     * @param type key
     * @return
     */
    public static String mbTypeToValue(Integer type) {
        return mbMap.get(type);
    }

    /**
     * 根据公告类型的值获取key
     *
     * @param typeValue 公告类型的值
     * @return
     */
    public static Integer mbTypeToValue(String typeValue) {
        if (StringUtils.isNotBlank(typeValue)) {
            for (Map.Entry<Integer, String> entry : mbMap.entrySet()) {
                if (entry.getValue().equals(typeValue.trim())) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }


    /**
     * 获取key对应的去向类型
     *
     * @param got key
     * @return
     */
    public static String leaveGotToGotValue(Integer got) {
        return leaveMap.get(got);
    }

    /**
     * 根据去向类型获取key
     *
     * @param gotValue 去向类型的值
     * @return
     */
    public static Integer leaveGotValueToGot(String gotValue) {
        if (StringUtils.isNotBlank(gotValue)) {
            for (Map.Entry<Integer, String> entry : leaveMap.entrySet()) {
                if (entry.getValue().equals(gotValue.trim())) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }

    /**
     * 获取type对应的批准/不批准
     *
     * @param type
     * @return
     */
    public static String stayinTypeToValue(Integer type) {
        return stayinMap.get(type);
    }

    public static Integer stayinValueToType(String value) {
        if (StringUtils.isNotBlank(value)) {
            for (Map.Entry<Integer, String> entry : stayinMap.entrySet()) {
                if (entry.getValue().equals(value.trim())) {
                    return entry.getKey();
                }
            }
        }
        return -1;
    }
}
