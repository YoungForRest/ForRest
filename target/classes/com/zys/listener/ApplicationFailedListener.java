package com.zys.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by YoungTH on 2018/10/26.
 * 应用启动成功监听
 */
@Component
public class ApplicationFailedListener implements ApplicationListener<ApplicationFailedEvent> {
    private Logger logger = LoggerFactory.getLogger(ApplicationFailedListener.class);

    @Override
    public void onApplicationEvent(ApplicationFailedEvent applicationFailedEvent) {
        logger.error(">>>>>>>系统启动失败<<<<<<<",applicationFailedEvent.getException());
    }
}
