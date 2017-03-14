package com.cn.mis.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * Description: HelloWorld！ <br/>
 * Autor: Created by jinkun on 2017/1/12.
 */
public class Md5Util {

    public static String md5(String str, String salt) {
        return new Md5Hash(str, salt, 1).toString();
    }

    public static String md5(String str, String salt, int hashIterations) {
        return new Md5Hash(str, salt, hashIterations).toString();
    }

}
