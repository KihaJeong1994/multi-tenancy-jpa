package com.example.multitenenttest.service.encrypt;

public interface EncryptionService {
    String encrypt(String strToEncrypt, String secret , String salt);
    String decrypt(String strToDecrypt, String secret , String salt);
}
