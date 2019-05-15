package com.youngth.zhou.is.core.aop;

import com.youngth.zhou.is.core.annotation.TryAgain;
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
public class TryAgainAspect implements Ordered {
    private Logger log = LoggerFactory.getLogger(TryAgainAspect.class);


    @Pointcut(value = "execution(* com.youngth.zhou.is.service.impl.*.*(..))")
    private void pointCut1(){

    }

    @Pointcut(value = "execution(* com.baomidou.mybatisplus.service.impl.ServiceImpl.*(..))")
    private void pointCut2(){

    }

    @Around("pointCut1()||pointCut2()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;

        Object target = point.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        TryAgain tryAgain = currentMethod.getAnnotation(TryAgain.class);

        if (tryAgain != null) {
            Integer tryTimes =Integer.parseInt( tryAgain.tryTimes());
            log.debug("设置异常次数为", tryTimes);
            Integer flag=0;
            while(true){
                try {
                    return point.proceed();
                }catch (Exception e){
                    flag++;
                    if(flag >= tryTimes){
                        throw e;
                    }
                }
            }
        }else {
            return point.proceed();
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
