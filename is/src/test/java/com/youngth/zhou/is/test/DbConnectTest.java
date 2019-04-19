package com.youngth.zhou.is.test;

import com.youngth.zhou.entity.ForrestUser;
import com.youngth.zhou.is.service.ForrestUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author YoungTH
 * @date 2019/4/18
 * 数据库连接测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbConnectTest {
    @Autowired
    private ForrestUserService forrestUserService;
    @Test
    public void fun1(){
        ForrestUser forrestUser = forrestUserService.selectById("865EC4A2175A42A6AB20B7E1D869515B");
        System.out.println(forrestUser);
    }
}
