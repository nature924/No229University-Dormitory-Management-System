package com.usc.lzh.doms.utils;

import java.lang.annotation.*;

/**
 * 自定义实体类所需要的bean(Excel属性标题、位置等)
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    /**
     * Excel标题
     *
     * @return
     * @author Lynch
     */
    String value() default "";

    /**
     * Excel从左往右排列位置
     *
     * @return
     * @author Lynch
     */
    int col() default 0;
}
