package com.youngth.zhou.is.service.impl;

import com.youngth.zhou.is.core.annotation.TryAgain;
import com.youngth.zhou.is.service.TestService;
import org.springframework.stereotype.Service;

/**
 * @author YoungTH
 * @date 2019/5/9
 */
@Service
public class TestServiceImpl implements TestService {

    @TryAgain(tryTimes = "3")
    @Override
    public void TestTryAgain() {
        System.out.println("尝试中。。。。");
        int i=1/0;
    }
}
