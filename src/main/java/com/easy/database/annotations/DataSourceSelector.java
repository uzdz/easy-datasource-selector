package com.easy.database.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 数据库选择器
 * @author uzdz
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Documented
public @interface DataSourceSelector {

    /**
     * 数据源 name
     * @return
     */
    String value();
}
