package com.lanxin.security;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;



public class RSAUtil {
    //初始化秘钥
	public static KeyPair getKeyPair() throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
	}
	//获取公钥(Base64编码)
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
       // System.out.println("Public Key:"+Base64.encodeBase64String(bytes));
        return Base64.encodeBase64String(bytes);
    }
    //获取私钥(Base64编码)
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        System.out.println("Private Key:"+Base64.encodeBase64String(bytes));
        return Base64.encodeBase64String(bytes);
    }
  //公钥加密
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        System.out.println("Public Key 加密："+Base64.encodeBase64String(bytes));
        return bytes;
    }
    
    //私钥加密
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        System.out.println("Private Key 解密："+Base64.encodeBase64String(bytes));
        return bytes;
    }
    //私钥解密
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = Base64.decodeBase64(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }
    
    //公钥解密
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = Base64.decodeBase64(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }
	
}
