package com.youngth.zhou.is.core.dataSource;

/**
 * @author YoungTH
 * @date 2019/4/18
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    /**
     * 设置数据源名称
     *
     * @param dataSource 数据源名称
     */
    public static void setDataSource(String dataSource) {
        contextHolder.set(dataSource);
    }

    /**
     * 获取数据源
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 清除数据源
     */
    public static void clearDataSource() {
        contextHolder.remove();
    }
}
