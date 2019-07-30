package com.easy.database.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.easy.database.properties.DataSourceProperties;
import com.easy.database.properties.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 数据源
 * @author uzdz
 */
@Order(0)
@Configuration
@EnableConfigurationProperties(DataSourceProperties.class)
public class DataSourceConfig {

    private final DataSourceProperties dataSourceProperties;

    public DataSourceConfig(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }

    @Bean("dataSource")
    public AbstractRoutingDataSource dynamicSource() {
        AbstractRoutingDataSource abstractRoutingDataSource = new AbstractRoutingDataSource() {
            @Override
            protected Object determineCurrentLookupKey() {
                return DataSourceContextHolder.getDB();
            }
        };

        Optional<Resource> defaultResource = dataSourceProperties.getDatasource().stream()
                .filter((data) -> data.isDefaultDataSource()).findFirst();

        if (!defaultResource.isPresent()) {
            throw new RuntimeException("default database can not be null!");
        }

        Map<Object, Object> resource = new HashMap<>(16);

        dataSourceProperties.getDatasource().stream().forEach((source) -> {

            DruidDataSource druidDataSource = new DruidDataSource();

            BeanUtils.copyProperties(source, druidDataSource);

            resource.put(source.getName(), druidDataSource);

            if (source.isDefaultDataSource()) {
                abstractRoutingDataSource.setDefaultTargetDataSource(druidDataSource);
            }
        });

        abstractRoutingDataSource.setTargetDataSources(resource);

        return abstractRoutingDataSource;
    }
}
