package com.pandatom.example;

import com.pandatom.example.util.PasswordUtil;
import org.apache.shiro.codec.Base64;

/**
 * @Description: TODO
 * @author: changxiong
 * @date: 2022年04月07日 4:53 PM
 */
public class testPassword {
    public static void main(String[] args) {
        String encrypt = PasswordUtil.encrypt("asdfdasfasdfasdf", encode64("123"), "123");
        System.out.println(encrypt);
    }

    public static String encode64(String str) {
        byte[] b = str.getBytes();
        Base64 base64 = new Base64();
        b = base64.encode(b);
        String s = new String(b);
        return s;
    }

}
