package com.xiaohu.cloud_notebook_common.util;

/**
 * @author xiaohu
 * @date 2022/11/09/ 16:10
 * @description 保存用户登录
 */
public class Holder {
    private static final ThreadLocal tl = new ThreadLocal<>();
    public static void save(Object obj){
        tl.set(obj);
    }

    public static Object get(){
        return tl.get();
    }

    public static void remove(){
        tl.remove();
    }
}
