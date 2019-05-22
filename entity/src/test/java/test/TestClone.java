package test;

import com.youngth.zhou.entity.TestEntity;
import org.junit.Test;

/**
 * @author YoungTH
 * @date 2019/5/22
 */
public class TestClone {
    @Test
    public void fun1(){
        TestEntity testEntity = new TestEntity();
        testEntity.setName("张三");
        testEntity.setAge(18);
        TestEntity testEntity1 = testEntity.serializableClone();
        testEntity1.setAge(22);
        System.out.println(testEntity);
        System.out.println(testEntity1);
    }
}
