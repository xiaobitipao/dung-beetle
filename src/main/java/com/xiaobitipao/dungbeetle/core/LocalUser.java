package com.xiaobitipao.dungbeetle.core;

import com.xiaobitipao.dungbeetle.model.User;

import java.util.HashMap;
import java.util.Map;

public class LocalUser {

    /**
     * 静态变量保存数据但不能保存状态，所以需要对状态进行区分
     *
     * 单纯使用 map 管理数据的话，当用户退出的话还需要手动管理 map 数据，处理起来比较麻烦
     *
     * TheadLocal 功能上类似于 Map，但它的 key 不需要指定，而是当前线程的标识
     *
     * TODO:
     * 如果在拦截器中，key=userId, value=User/Scope，并将 key/value 保存到 redis 中
     * 当请求来的时候，各自用各自获取到的 userId 去 redis 中查询 user 信息，这样是不是就不需要考虑线程安全了呢。。。
     */
    private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    /**
     * 设置线程标识对应的值，一般在进入拦截器时进行设置
     */
    public static void set(User user, Integer scope) {
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        LocalUser.threadLocal.set(map);
    }

    /**
     * 清除线程标识对应的值，一般在退出拦截器时进行清除
     */
    public static void clear() {
        LocalUser.threadLocal.remove();
    }

    /**
     * 获取线程标识中对应的用户信息
     * 这里之所以是 User 而不是 userId 是因为如果需要的话方便获取 user 其他的信息
     */
    public static User getUser() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        return (User) map.get("user");
    }

    /**
     * 获取用户 scope
     */
    public static Integer getScope() {
        Map<String, Object> map = LocalUser.threadLocal.get();
        return (Integer) map.get("scope");
    }
}
