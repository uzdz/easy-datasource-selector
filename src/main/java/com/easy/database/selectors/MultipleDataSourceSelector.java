package com.easy.database.selectors;

import com.easy.database.aop.DynamicDataSourceAspect;
import com.easy.database.annotations.EnableMultipleDataSource;
import com.easy.database.config.DataSourceConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Arrays;
import java.util.List;

/**
 * 启动器
 * @author uzdz
 */
public class MultipleDataSourceSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableMultipleDataSource.class.getName()));

        boolean startup = attributes.getBoolean("startup");

        String[] component = new String[2];
        if (startup) {
            component[0] = DataSourceConfig.class.getName();
            component[1] = DynamicDataSourceAspect.class.getName();
        }

        List<String> upAssembly = Arrays.asList(component);
        if (upAssembly.contains(DataSourceConfig.class.getName()) &&
                upAssembly.contains(DynamicDataSourceAspect.class.getName())) {
            return component;
        } else {
            return new String[0];
        }
    }
}
