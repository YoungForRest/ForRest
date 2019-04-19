package com.youngth.zhou.baseEnum;

/**
 * @author YoungTH
 * @date 2019/4/18
 * 测试枚举类
 */
public enum TestEnum {
    //测试使用
    TEST("Test","测试"),
    ;

    /**
     * 返回枚举代码
     */
    private String code;

    /**
     * 返回枚举代码描述
     */
    private String desc;

    /**
     * 构造
     * @param code 枚举代码
     * @param desc 枚举代码描述
     */
    TestEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 返回枚举代码
     *
     * @return 枚举代码
     */
    public String code() {
        return code;
    }
    /**
     * 返回枚举代码描述
     *
     * @return 枚举代码描述
     */
    public String desc() {
        return desc;
    }


}
