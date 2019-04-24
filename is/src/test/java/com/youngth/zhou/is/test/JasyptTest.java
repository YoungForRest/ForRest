package com.youngth.zhou.is.test;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author YoungTH
 * @date 2019/4/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JasyptTest {
    @Resource(name="jasyptStringEncryptor")
    private StringEncryptor stringEncryptor;
    @Test
    public void fun1(){
        String zys123 = stringEncryptor.encrypt("zys123");
        System.out.println(zys123);
        String decrypt = stringEncryptor.decrypt(zys123);
        System.out.println(decrypt);
    }
}
