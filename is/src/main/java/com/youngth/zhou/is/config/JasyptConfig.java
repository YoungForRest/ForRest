package com.youngth.zhou.is.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YoungTH
 * @date 2019/4/24
 * Jasypt自定义加密器
 */
@Configuration
public class JasyptConfig {
    private Logger logger = LoggerFactory.getLogger(JasyptConfig.class);

    /**
     * For custom configuration of the encryptor
     *   and the source of the encryptor password
     *     you can always define your own StringEncryptor bean in your Spring Context,
     * and the default encryptor will be ignored.
     *
     * @return
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword("~zysljy.1314~");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
//        if you would like to use the newer algorithms in Java 8+ (e.g. PBEWITHHMACSHA512ANDAES_256) you must set this value to org.jasypt.salt.RandomIVGenerator.
//        config.setIvGeneratorClassName("org.jasypt.salt.NoOpIVGenerator");
        config.setIvGeneratorClassName("org.jasypt.salt.RandomIVGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }

    @Bean(name = "encryptablePropertyDetector")
    public EncryptablePropertyDetector encryptablePropertyDetector() {
        return new MyEncryptablePropertyDetector();
    }

}
