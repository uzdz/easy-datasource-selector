package com.easy.database.config;

/**
 * 数据库上下文
 * @author uzdz
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> handler = new ThreadLocal<String>();

    public static void  setDB(String db){
        handler.set(db);
    }

    public static String getDB(){
        return handler.get();
    }

    public static void clearDB(){
        handler.remove();
    }
}
