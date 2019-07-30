package com.easy.database.properties;

/**
 * 数据库配置
 * @author uzdz
 */
public class Resource {

    /**
     * 数据库名称
     */
    private String name;

    /**
     * 驱动地址
     */
    private String driverClassName;

    /**
     * 数据库链接
     */
    private String url;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 默认数据库
     */
    private boolean defaultDataSource;

    public boolean isDefaultDataSource() {
        return defaultDataSource;
    }

    public void setDefaultDataSource(boolean defaultDataSource) {
        this.defaultDataSource = defaultDataSource;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
