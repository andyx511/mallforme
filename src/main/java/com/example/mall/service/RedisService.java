package com.example.mall.service;

public interface RedisService {
    /**
     * Description 存储数据
     */
    void set(String key ,String value);
    /**
     * Description 获取数据
     */
    String get(String key);
    /**
     * Description 设置超时时间
     */
    boolean expire(String key,long expire);
    /**
     * Description 删除数据
     */
    void remove (String key);
    /**
     * Description 自增操作
     */
    Long increment(String key,long data);

}
