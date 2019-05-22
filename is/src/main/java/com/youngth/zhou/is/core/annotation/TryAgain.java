package com.youngth.zhou.is.core.annotation;

import java.lang.annotation.*;

/**
 * @author YoungTH
 * @date 2019/4/18
 * 继续尝试
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface TryAgain {
    /**
     * 继续尝试
     */
    String tryTimes() default "3";
}
