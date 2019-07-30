package com.easy.database.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据源配置信息
 * @author uzdz
 */
@ConfigurationProperties(prefix = "geek.multiple")
public class DataSourceProperties {

    private List<Resource> datasource = new ArrayList();

    public List<Resource> getDatasource() {
        return datasource;
    }

    public void setDatasource(List<Resource> datasource) {
        this.datasource = datasource;
    }
}
