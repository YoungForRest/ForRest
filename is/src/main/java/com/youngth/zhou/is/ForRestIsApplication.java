package com.youngth.zhou.is;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author YoungTH
 * @date 2019/4/17
 * is 接口服务启动类
 */
@SpringBootApplication
@EnableEncryptableProperties
@EnableScheduling
public class ForRestIsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForRestIsApplication.class, args);
    }
}
