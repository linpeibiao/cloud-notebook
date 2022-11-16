package com.xiaohu.cloud_notebook_common.util;

import com.xiaohu.cloud_notebook_common.model.domain.User;

/**
 * @author xiaohu
 * @date 2022/11/09/ 16:10
 * @description 保存用户登录
 */
public class UserHolder<T> {
    private static final ThreadLocal<User> tl = new ThreadLocal<>();
    public static void save(User user){
        tl.set(user);
    }

    public static User get(){
        return tl.get();
    }

    public static void remove(){
        tl.remove();
    }
}
