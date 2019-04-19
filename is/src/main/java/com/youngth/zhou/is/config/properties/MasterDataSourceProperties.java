package com.youngth.zhou.is.config.properties;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

/**
 * @author YoungTH
 * @date 2019/4/17
 * 主数据库配置
 */
@Component
@ConfigurationProperties(prefix = MasterDataSourceProperties.PREFIX)
public class MasterDataSourceProperties {
    public static final String PREFIX="datasource.sources.master";

    private String url="";
    private String username="";
    private String password;

    /**
     * 默认只启用SQL防火墙过滤器
     * druid.filters.default=com.alibaba.druid.filter.stat.StatFilter
     * druid.filters.stat=com.alibaba.druid.filter.stat.StatFilter
     * druid.filters.mergeStat=com.alibaba.druid.filter.stat.MergeStatFilter
     * druid.filters.counter=com.alibaba.druid.filter.stat.StatFilter
     * druid.filters.encoding=com.alibaba.druid.filter.encoding.EncodingConvertFilter
     * druid.filters.log4j=com.alibaba.druid.filter.logging.Log4jFilter
     * druid.filters.log4j2=com.alibaba.druid.filter.logging.Log4j2Filter
     * druid.filters.slf4j=com.alibaba.druid.filter.logging.Slf4jLogFilter
     * druid.filters.commonlogging=com.alibaba.druid.filter.logging.CommonsLogFilter
     * druid.filters.commonLogging=com.alibaba.druid.filter.logging.CommonsLogFilter
     * druid.filters.wall=com.alibaba.druid.wall.WallFilter
     * druid.filters.config=com.alibaba.druid.filter.config.ConfigFilter
     */
    private String filters = "wall";

    public void config(DruidDataSource druidDataSource) throws SQLException {
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setFilters(filters);
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

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }
}
