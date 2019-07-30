package com.easy.database.aop;

import com.easy.database.config.DataSourceContextHolder;
import com.easy.database.annotations.DataSourceSelector;
import com.easy.database.properties.DataSourceProperties;
import com.easy.database.properties.Resource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 数据源切面
 * @author uzdz
 */
@Order(-1)
@Aspect
@Component
@EnableConfigurationProperties(DataSourceProperties.class)
public class DynamicDataSourceAspect {

    private final DataSourceProperties dataSourceProperties;

    public DynamicDataSourceAspect(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Pointcut("@annotation(com.easy.database.annotations.DataSourceSelector)")
    public void selector(){}

    @Before("selector()")
    public void before(JoinPoint point){

        List<String> dataSource = dataSourceProperties.getDatasource().stream()
                .map(datasource -> datasource.getName())
                .collect(Collectors.toList());

        MethodSignature methodSignature = (MethodSignature) point.getSignature();

        DataSourceSelector annotation = methodSignature.getMethod()
                .getAnnotation(DataSourceSelector.class);

        if(annotation != null && dataSource.contains(annotation.value())){

            DataSourceContextHolder.setDB(annotation.value());

        } else {

            Optional<Resource> defaultDataSource = dataSourceProperties.getDatasource().stream()
                    .filter((data) -> data.isDefaultDataSource()).findFirst();

            DataSourceContextHolder.setDB(defaultDataSource.get().getName());
        }
    }

    @After("selector()")
    public void after(){
        DataSourceContextHolder.clearDB();
    }

}
