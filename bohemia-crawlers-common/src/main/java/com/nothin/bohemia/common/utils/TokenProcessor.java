package com.nothin.bohemia.common.utils;

import java.security.MessageDigest;  
import java.security.NoSuchAlgorithmException;  
import java.util.Random;  
import sun.misc.BASE64Encoder;  
  
//令牌生产器  
public class TokenProcessor {  
    private TokenProcessor(){}  
    private static TokenProcessor instance = new TokenProcessor();  
    public static TokenProcessor getInstance(){  
        return instance;  
    }  
    public String generateTokeCode(){  
        String value = System.currentTimeMillis()+new Random().nextInt()+"";  
        //获取数据指纹，指纹是唯一的  
        try {  
            MessageDigest md = MessageDigest.getInstance("md5");  
            byte[] b = md.digest(value.getBytes());//产生数据的指纹  
            //Base64编码  
            BASE64Encoder be = new BASE64Encoder();  
            be.encode(b);  
            return be.encode(b);//制定一个编码  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }  
          
        return null;  
    }
    
    /**
     * 传入数据将其md5加密生成一个字符串
     * @param url
     * @return
     */
    public String generateTokeCode(String url){
    	String md5Str = "";
        //获取数据指纹，指纹是唯一的  
        try {  
            MessageDigest md = MessageDigest.getInstance("md5");  
            byte[] b = md.digest(url.getBytes());//产生数据的指纹  
            //Base64编码  
            BASE64Encoder be = new BASE64Encoder();  
            md5Str = be.encode(b);  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        }
        return md5Str;
    }
}  