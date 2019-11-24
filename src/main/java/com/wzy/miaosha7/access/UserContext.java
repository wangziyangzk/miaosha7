package com.wzy.miaosha7.access;

import com.wzy.miaosha7.domain.MiaoshaUser;

public class UserContext {

    public static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }
}
