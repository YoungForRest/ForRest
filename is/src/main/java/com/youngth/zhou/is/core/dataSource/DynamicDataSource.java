package com.youngth.zhou.is.core.dataSource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author YoungTH
 * @date 2019/4/18
 * 继承了AbstractDataSource：
 * determineTargetDataSource
 * determineCurrentLookupKey();//业务代码能更改这个值，就可使用指定的DB
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDataSource();
    }
}
