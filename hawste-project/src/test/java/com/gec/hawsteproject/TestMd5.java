package com.gec.hawsteproject;

import com.gec.hawsteproject.hawaste.utils.EncryptUtils;
import org.junit.jupiter.api.Test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class TestMd5 {
    /**
     * 模拟MD5摘要算法加密流程
     * 1.从字符串中获取字节数组
     * 2.加密处理字节数组
     * 3.将处理后的字节数组,提取出摘要信息,得到处理完毕的加密数组
     * 4.将加密后的字节数组转成特定格式的字符串
     */
    @Test
    public void testDigest() throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = "admin".getBytes();//字符串的字节数据
        //处理原文的字节数组
        md5.update(bytes);
        //摘要运算
        byte[] digest = md5.digest();
        System.out.println(Arrays.toString(digest));
        /*String s = new String(digest);
        System.out.println(s);*/
        System.out.println(EncryptUtils.toHexString(digest));
    }

    /**
     * md5加密:
     * 用户名13637920177   密码123456    加密
     * 1.对密码进行md5加密,得到加密后的字符串
     * 2.字符串拼接用户名,再进行md5加密
     *
     * 加密后:a8ce08e02a1caada62116f28f5ed8a02
     *
     * 数据库:a8ce08e02a1caada62116f28f5ed8a02
     */
    @Test
    public void testDigest2(){
        String md5_hex = EncryptUtils.MD5_HEX(EncryptUtils.MD5_HEX("123456") + "13637920177");
        System.out.println(md5_hex);
    }

}
