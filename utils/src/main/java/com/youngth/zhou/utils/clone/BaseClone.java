package com.youngth.zhou.utils.clone;

import java.io.Serializable;

/**
 * @author YoungTH
 * @date 2019/5/22
 */
public class BaseClone<T> implements Serializable, Cloneable {
    private static final long serialVersionUID = -436883860034791729L;

    /**
     * 浅克隆
     * @return
     */
    @Override
    public T clone() {
        try {
            return (T) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 序列化克隆 深度克隆
     * @return
     */
    public T serializableClone() {
        T t = SerializableCloneUtil.serializableClone((T) this);
        return t;
    }


}
