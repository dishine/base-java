package com.shinedi.javabase.common.constant;

import com.shinedi.javabase.common.util.RedisUtil;

/**
 * @author D-S
 * Created on 2019/12/9 11:36 上午
 */
public class RedisLock {

    /**
     * 加锁
     */
    public synchronized static boolean lock(String methodName) {
        String key = ApiConstants.REDIS_LOCK_KEY_WALLET+methodName;
        boolean locked = RedisUtil.setnx(key, "lock");
        if (locked) {
            // 设置60s的超时时间，以免线程阻塞导致其他线程无法获取锁
            RedisUtil.expire(key, 60);
        }

        return locked;
    }

    /**
     * 查看是否加锁
     */
    public synchronized static boolean isLocked(String methodName) {
        String key = ApiConstants.REDIS_LOCK_KEY_WALLET+methodName;
        return RedisUtil.get(key) != null;
    }

    /**
     * 解锁
     */
    public synchronized static void unlock(String methodName) {
        String key = ApiConstants.REDIS_LOCK_KEY_WALLET+methodName;
        RedisUtil.del(key);
    }
}
