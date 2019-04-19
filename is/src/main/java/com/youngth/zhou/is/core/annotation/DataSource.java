package com.youngth.zhou.is.core.annotation;

import com.youngth.zhou.baseEnum.Database;

import java.lang.annotation.*;

/**
 * @author YoungTH
 * @date 2019/4/18
 * 数据源切换注解
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataSource {
    /**
     * 设置数据源
     *
     * @return 默认设置为主数据库
     */
    Database db() default Database.DB_MASTER;
}
