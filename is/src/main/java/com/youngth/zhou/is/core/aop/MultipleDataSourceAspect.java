package com.youngth.zhou.is.core.aop;

import com.youngth.zhou.baseEnum.Database;
import com.youngth.zhou.is.core.annotation.DataSource;
import com.youngth.zhou.is.core.dataSource.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author YoungTH
 * @date 2019/4/18
 * 数据源切换  切面
 * AOP
 */
@Aspect
@Component
public class MultipleDataSourceAspect implements Ordered {
    private Logger log = LoggerFactory.getLogger(MultipleDataSourceAspect.class);

    @Pointcut(value = "@annotation(com.youngth.zhou.is.core.annotation.DataSource)")
    private void pointCut() {

    }

    @Pointcut(value = "execution(* com.youngth.zhou.is.service.impl.*.*(..))")
    private void pointCut2(){

    }

    @Pointcut(value = "execution(* com.baomidou.mybatisplus.service.impl.ServiceImpl.*(..))")
    private void pointCut3(){

    }

    @Around("pointCut3()||pointCut2()|| pointCut()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        DataSource datasource = currentMethod.getAnnotation(DataSource.class);
        if (datasource != null) {
            DataSourceContextHolder.setDataSource(datasource.db().id());
            log.debug("设置数据源为：{}", datasource.db().desc());
        } else {
            DataSourceContextHolder.setDataSource(Database.DB_MASTER.id());
            log.debug("设置数据源为：{}", Database.DB_MASTER.desc());
        }

        try {
            return point.proceed();
        } finally {
            log.debug("清空数据源信息！");
            DataSourceContextHolder.clearDataSource();
        }
    }


    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
