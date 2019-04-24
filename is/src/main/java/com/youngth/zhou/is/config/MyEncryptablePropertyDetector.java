package com.youngth.zhou.is.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;

/**
 * @author YoungTH
 * @date 2019/4/24
 * 自定义属性检测器  负责检测加密属性
 */
public class MyEncryptablePropertyDetector implements EncryptablePropertyDetector {
    @Override
    public boolean isEncrypted(String value) {
        if (value != null) {
            return value.startsWith("YoungTH@@");
        }
        return false;
    }

    @Override
    public String unwrapEncryptedValue(String value) {
        return value.substring("YoungTH@@".length());
    }
}
