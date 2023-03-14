package com.run.core.io;

/**
 * @desc: 资源加载器：把不同的资源加载方式集中到该类，
 * 外部只需传递资源地址进来即可，简化使用。
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public interface ResourceLoader {

    /**
     * 类路径的前缀，指定 ClassPath 有统一的前缀 classpath:
     */
    String CLASSPATH_URL_PREFIX = "classpath:";

    /**
     * 获取具体资源的接口
     * @param location 资源的 location
     * @return
     */
    Resource getResource(String location);
}
