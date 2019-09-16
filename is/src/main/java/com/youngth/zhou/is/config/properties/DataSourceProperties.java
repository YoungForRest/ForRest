package com.youngth.zhou.is.config.properties;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Slf4jLogFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcUtils;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author YoungTH
 * @date 2019/4/17
 * druid 连接池基础配置
 */
@Component
public class DataSourceProperties {

    @Value("${datasource.dbtype}")
    private String dbtype = "";
    /**
     * 数据库驱动类名
     * druid 会根据url 自动识别
     */
    private String driverClassName = "";
    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialSize = 1;
    /**
     * 最大连接池数量
     */
    private Integer maxActive = 8;
    /**
     * 最小连接池数量
     */
    private Integer minIdle = 1;
    /**
     * 获取连接时最大等待时间，单位毫秒。
     * 配置了maxWait之后，缺省启用公平锁，并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Integer maxWait = 60000;
    /**
     * 是否缓存preparedStatement，也就是PSCache。
     * PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private boolean poolPreparedStatements = true;
    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private Integer maxOpenPreparedStatements = 10;
    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会其作用。
     */
    private String validationQuery = "SELECT 'A' FROM DUAL";
    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private boolean testOnBorrow = false;
    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private boolean testOnReturn = false;
    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private boolean testWhileIdle = true;
    /**
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private Integer timeBetweenEvictionRunsMillis = 60000;
    /**
     * Destory线程中如果检测到当前连接的最后活跃时间和当前时间的差值大于
     * minEvictableIdleTimeMillis，则关闭当前连接。
     */
    private Integer minEvictableIdleTimeMillis = 300000;
    /**
     * 物理连接初始化的时候执行的sql
     */
    private String connectionInitSqls = "";
    /**
     * 当数据库抛出一些不可恢复的异常时，抛弃连接
     */
    private String exceptionSorter;
    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，
     * 常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private String filters = "stat";
    /**
     * 类型是List<com.alibaba.druid.filter.Filter>，
     * 如果同时配置了filters和proxyFilters，
     * 是组合关系，并非替换关系
     */
    private List<Filter> proxyFilters;
    /**
     * 打开PSCache，并且指定每个连接上PSCache的大小
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = 20;


    public void congfig(DruidDataSource dataSource) {
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);     //定义初始连接数
        dataSource.setMinIdle(minIdle);             //最小空闲
        dataSource.setMaxActive(maxActive);         //定义最大连接数
        dataSource.setMaxWait(maxWait);             //最长等待时间

        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);

        // 配置一个连接在池中最小生存的时间，单位是毫秒
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);

        // 打开PSCache，并且指定每个连接上PSCache的大小
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            dataSource.setFilters(filters);
            dataSource.setProxyFilters(customFilter());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 自定义过滤器设置
     *
     * @return 自定义过滤器列表
     */
    private List<Filter> customFilter() {
        List<Filter> filters = new ArrayList();
        filters.add(slf4jLogFilter());
        filters.add(statFilter());
        filters.add(wallFilter());
        return filters;
    }

    /**
     * 日志记录过滤器
     *
     * @return Slf4jLogFilter
     */
    private Slf4jLogFilter slf4jLogFilter() {
        Slf4jLogFilter slf4jLogFilter = new Slf4jLogFilter();
        //禁用数据源日志
        slf4jLogFilter.setDataSourceLogEnabled(false);
        //禁用连接日志
        slf4jLogFilter.setConnectionLogEnabled(false);
        //禁用预执行语句日志
        slf4jLogFilter.setStatementLogEnabled(false);
        //禁用结果集日志
        slf4jLogFilter.setResultSetLogEnabled(false);
        //输出可执行的SQL
        slf4jLogFilter.setStatementExecutableSqlLogEnable(true);
        return slf4jLogFilter;
    }

    /**
     * 统计过滤器设置
     *
     * @return StatFilter
     */
    private StatFilter statFilter() {
        StatFilter statFilter = new StatFilter();
        //SQL合并配置
        statFilter.setMergeSql(true);
        //启用慢SQL记录功能
        statFilter.setLogSlowSql(true);
        //慢SQL时间（10秒）
        statFilter.setSlowSqlMillis(10000);
        return statFilter;
    }

    /**
     * SQL防御
     *
     * @return WallFilter
     */
    private WallFilter wallFilter() {
        WallFilter wallFilter = new WallFilter();
        wallFilter.setDbType(dbtype);
        return wallFilter;
    }

    public String getDbtype() {
        return dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public boolean isPoolPreparedStatements() {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements) {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public Integer getMaxOpenPreparedStatements() {
        return maxOpenPreparedStatements;
    }

    public void setMaxOpenPreparedStatements(Integer maxOpenPreparedStatements) {
        this.maxOpenPreparedStatements = maxOpenPreparedStatements;
    }

    public String getValidationQuery() {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery) {
        this.validationQuery = validationQuery;
    }

    public boolean isTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }

    public boolean isTestWhileIdle() {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle) {
        this.testWhileIdle = testWhileIdle;
    }

    public Integer getTimeBetweenEvictionRunsMillis() {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(Integer timeBetweenEvictionRunsMillis) {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public Integer getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Integer minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getConnectionInitSqls() {
        return connectionInitSqls;
    }

    public void setConnectionInitSqls(String connectionInitSqls) {
        this.connectionInitSqls = connectionInitSqls;
    }

    public String getExceptionSorter() {
        return exceptionSorter;
    }

    public void setExceptionSorter(String exceptionSorter) {
        this.exceptionSorter = exceptionSorter;
    }

    public String getFilters() {
        return filters;
    }

    public void setFilters(String filters) {
        this.filters = filters;
    }

    public List<Filter> getProxyFilters() {
        return proxyFilters;
    }

    public void setProxyFilters(List<Filter> proxyFilters) {
        this.proxyFilters = proxyFilters;
    }

    public Integer getMaxPoolPreparedStatementPerConnectionSize() {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(Integer maxPoolPreparedStatementPerConnectionSize) {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }
}
