package com.easy.database.annotations;

import com.easy.database.selectors.MultipleDataSourceSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 开启多数据源
 * @author uzdz
 */
@Retention(value = java.lang.annotation.RetentionPolicy.RUNTIME)
@Target(value = {ElementType.TYPE})
@Documented
@Import(MultipleDataSourceSelector.class)
public @interface EnableMultipleDataSource {

    /**
     * 启动多数据源
     * @return the default value is false
     */
    boolean startup() default true;
}
