package com.asaraff.gateway.services;


import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

/**
 * Uses JASYPT..A simple one with password
 */
public class EncryptionService {
    private final StandardPBEStringEncryptor stringEncryptor;

    public EncryptionService(StandardPBEStringEncryptor stringEncryptor) {
        this.stringEncryptor = stringEncryptor;
    }


    public String encrypt(String password) {
        return stringEncryptor.encrypt(password);
    }

    public String decrypt(String encryptedText) {
        return stringEncryptor.decrypt(encryptedText);
    }
}
