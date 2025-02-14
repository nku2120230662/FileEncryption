package com.guohao.service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class FileEncrypt{

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";



    // 加密文件
    public static void encryptFile(SecretKey secretKey, byte[] iv, String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

        try (InputStream inputStream = new FileInputStream(inputFile);
             OutputStream outputStream = new FileOutputStream(outputFile);
             CipherOutputStream cipherOutputStream = new CipherOutputStream(outputStream, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                cipherOutputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    // 解密文件
    public static void decryptFile(SecretKey secretKey, byte[] iv, String inputFile, String outputFile) throws Exception {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        try (InputStream inputStream = new FileInputStream(inputFile);
             CipherInputStream cipherInputStream = new CipherInputStream(inputStream, cipher);
             OutputStream outputStream = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cipherInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    // 生成 AES 密钥
    public static SecretKey generateKey(int keySize) throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(keySize);
        return keyGen.generateKey();
    }

    // 生成初始化向量 (IV)
    public static byte[] generateIv() throws Exception {
        byte[] iv = new byte[16]; // AES block size is 16 bytes
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        return iv;
    }
}
