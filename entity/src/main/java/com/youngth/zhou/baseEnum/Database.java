package com.youngth.zhou.baseEnum;

/**
 * @author YoungTH
 * @date 2019/4/18
 */
public enum Database {
    //定义数据库枚举
    DB_MASTER("DB_MASTER", "主数据库"),
    ;

    private String id;
    private String desc;

    Database(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String id() {
        return id;
    }

    public String desc() {
        return desc;
    }

    @Override
    public String toString() {
        return "{" + String.valueOf(id()) + "=" + desc() + "}";
    }



}
