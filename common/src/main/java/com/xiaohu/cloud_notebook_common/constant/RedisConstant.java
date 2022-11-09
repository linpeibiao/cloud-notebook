package com.xiaohu.cloud_notebook_common.constant;

/**
 * @author xiaohu
 * @date 2022/11/09/ 16:48
 * @description redis key常量
 */
public class RedisConstant {
    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final long LOGIN_CODE_TTL = 2L;


    public static final String LOGIN_USER_KEY = "login:user:";
    public static final long LOGIN_USER_TTL = 30L;

}
