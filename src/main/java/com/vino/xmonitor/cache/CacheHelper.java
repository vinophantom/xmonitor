package com.vino.xmonitor.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.vino.xmonitor.service.UserService;

import java.util.concurrent.TimeUnit;

/**
 * @author phantom
 */
public class CacheHelper {


    /**
     * guava缓存
     * 两秒过期
     */
    private static Cache<String, Object> loadingCache = CacheBuilder.newBuilder()
            /*设置缓存容器的初始容量大小为10*/
            .initialCapacity(32)
            /*设置缓存容器的最大容量大小为100*/
            .maximumSize(1024)
            /*设置记录缓存命中率*/
            .recordStats()
            /*设置并发级别为8，同时缓存的线程数*/
            .concurrencyLevel(8)
            /*设置过期时间*/
            .expireAfterAccess(100, TimeUnit.SECONDS)
            .build();
    /**
     * 持久缓存
     */
    private static Cache<String, Object> persisCache = CacheBuilder.newBuilder()
            /*设置缓存容器的初始容量大小为10*/
            .initialCapacity(32)
            /*设置缓存容器的最大容量大小为100*/
            .maximumSize(1024)
            /*设置记录缓存命中率*/
            .recordStats()
            /*设置并发级别为8，同时缓存的线程数*/
            .concurrencyLevel(8)
            .build();


    private static Cache<String, Object> loginCache = CacheBuilder.newBuilder()
            /*设置缓存容器的初始容量大小为10*/
            .initialCapacity(32)
            /*设置缓存容器的最大容量大小为100*/
            .maximumSize(1024)
            /*设置记录缓存命中率*/
            .recordStats()
            /*设置并发级别为8，同时缓存的线程数*/
            .concurrencyLevel(8)
            /*设置过期时间*/
            .expireAfterAccess(UserService.LOGIN_AGE, TimeUnit.SECONDS)
            .build();
    /**
     * 保存至LoadingCache
     * @param key
     * @param value
     */
    public static void saveToLoadingCache(String key, Object value) { loadingCache.put(key, value); }


    /**
     * 从LoadingCache中取出
     * @param key
     * @return
     */
    public static Object getFromLoadingCache(String key) { return loadingCache.getIfPresent(key); }



    /**
     * 保存至persisCache
     * @param key
     * @param value
     */
    public static void saveToPersisCache(String key, Object value) { persisCache.put(key, value); }


    /**
     * 从persisCache中取出
     * @param key
     * @return
     */
    public static Object getFromPersisCache(String key) { return persisCache.getIfPresent(key); }



    public static void deleteFromPersisCache(String key) { persisCache.invalidate(key); }


    /**
     * 保存至persisCache
     * @param key
     * @param value
     */
    public static void saveToLoginCache(String key, Object value) { loginCache.put(key, value); }


    /**
     * 从persisCache中取出
     * @param key
     * @return
     */
    public static Object getFromLoginCache(String key) { return loginCache.getIfPresent(key); }



    public static void deleteFromLoginCache(String key) { loginCache.invalidate(key); }

}
