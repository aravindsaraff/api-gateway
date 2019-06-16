package com.asaraff.gateway.config;

import com.asaraff.gateway.services.EncryptionService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class EncryptionConfig {

    @Value("${encryption.password}")
    private String encryptionPassword;

    @Bean
    public StandardPBEStringEncryptor stringEncryptor() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setProvider(new BouncyCastleProvider());
        encryptor.setAlgorithm("PBEWITHSHA256AND128BITAES-CBC-BC");
        encryptor.setPassword(encryptionPassword);
        return encryptor;
    }

    @Bean
    public EncryptionService encryptionService() {
        return new EncryptionService(stringEncryptor());
    }
}
