package com.youngth.zhou.is.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.plugins.PerformanceInterceptor;
import com.youngth.zhou.baseEnum.Database;
import com.youngth.zhou.is.config.properties.DataSourceProperties;
import com.youngth.zhou.is.config.properties.MasterDataSourceProperties;
import com.youngth.zhou.is.core.dataSource.DynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YoungTH
 * @date 2019/4/17
 * mybatisPlus配置类   采用多数据源配置方式，方便后期添加数据源
 */
@Configuration
@EnableTransactionManagement(order = 2)
@MapperScan(basePackages = {"com.youngth.zhou.is.mapper"})//mybatis 包扫描路径
public class MybatisPlusConfig implements TransactionManagementConfigurer {
    private Logger logger= LoggerFactory.getLogger(MybatisPlusConfig.class);

    /**
     * druid配置类
     */
    private DataSourceProperties dataSourceProperties;
    /**
     * 主数据库配置
     */
    private MasterDataSourceProperties masterDataSourceProperties;
    @Autowired
    public void setDataSourceProperties(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
    }
    @Autowired
    public void setMasterDataSourceProperties(MasterDataSourceProperties masterDataSourceProperties) {
        this.masterDataSourceProperties = masterDataSourceProperties;
    }


    /**
     * 主数据源
     */
    private DruidDataSource masterDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        dataSourceProperties.congfig(druidDataSource);
        masterDataSourceProperties.config(druidDataSource);
        return druidDataSource;
    }

    /**
     * 动态数据源配置
     */
    @Bean
    public DynamicDataSource roundRobinDataSourceProxy() {
        try {
            DynamicDataSource dynamicDataSource = new DynamicDataSource();
            Map<Object, Object> targetDataSources = new HashMap<>();
            DruidDataSource masterDataSource = masterDataSource();
            masterDataSource.init();
            logger.info("主数据库初始化完成！");
            targetDataSources.put(Database.DB_MASTER.id(),masterDataSource);

            dynamicDataSource.setTargetDataSources(targetDataSources);
            dynamicDataSource.setDefaultTargetDataSource(masterDataSource);
            return dynamicDataSource;
        }catch (SQLException ex){
            logger.error("加载数据源过程中出现异常：", ex);
            throw new RuntimeException(ex);
        }
    }
    /**
     * 事务配置,考虑多数据源情况下
     * @return
     */
    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(roundRobinDataSourceProxy());
    }


    /**
     * mybatis-plus分页插件<br>
     * 文档：http://mp.baomidou.com<br>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 开启 PageHelper 的支持
        paginationInterceptor.setLocalPage(true);
        return paginationInterceptor;
    }


    /**
     * 性能分析拦截器，不建议生产使用
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】
     */
    @Profile("dev")
    @Bean
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }
}
