package com.wzy.miaosha7.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    public static String md5(String str) {
        return DigestUtils.md5Hex(str);
    }

    private static final String salt = "abcd";

    public static String inputPassToFormPass(String inputPass){
        String newPass = "" + salt.charAt(0) + salt.charAt(1) + inputPass + salt.charAt(2) + salt.charAt(3);
        System.out.println(newPass);
        return md5(newPass);
    }

    public static String formPassToDBPass(String formPass,String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(1) + formPass + salt.charAt(2) + salt.charAt(3);
        return md5(str);
    }

    public static String inputPassToDBPass(String inputPass,String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass,saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        System.out.println(inputPassToDBPass("123445","abcd")); //ab123445cd cb4caebed581e27b896ec81578812443
    }
}
