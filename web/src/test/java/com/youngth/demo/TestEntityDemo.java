package com.youngth.demo;

import com.youngth.zhou.entity.TestEntity;
import org.junit.Test;

/**
 * @author YoungTH
 * @date 2019/4/11
 */
public class TestEntityDemo {
    @Test
    public void fun1(){
        TestEntity testEntity = new TestEntity();
        testEntity.setName("test");
        testEntity.setAge(18);
        System.out.println(testEntity);
    }
}
