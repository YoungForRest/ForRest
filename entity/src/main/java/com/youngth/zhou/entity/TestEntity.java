package com.youngth.zhou.entity;

import com.youngth.zhou.utils.clone.BaseClone;
import lombok.Data;

/**
 * @author YoungTH
 * @date 2019/4/11
 */
@Data
public class TestEntity extends BaseClone<TestEntity> {
    private String name;
    private Integer age;
}
