package com.guohao;

import javax.crypto.SecretKey;

import static com.guohao.service.FileEncrypt.*;

public class Main {
    public static void main(String[] args) {
        try {
            // 生成密钥和 IV
            SecretKey secretKey = generateKey(128); // 128-bit AES key
            byte[] iv = generateIv();

            // 原始文件路径和加密文件路径
            String inputFile = "input.txt"; // 替换为你的输入文件路径
            String encryptedFile = "encrypted.txt"; // 加密后文件路径
            String decryptedFile = "decrypted.txt"; // 解密后文件路径

            // 加密文件
            encryptFile(secretKey, iv, inputFile, encryptedFile);
            System.out.println("文件加密成功！");

            // 解密文件
            decryptFile(secretKey, iv, encryptedFile, decryptedFile);
            System.out.println("文件解密成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}