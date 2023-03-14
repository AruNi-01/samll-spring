package com.run.core.io;

import cn.hutool.core.lang.Assert;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @desc: 实现 ResourceLoader，实现 getResource() 方法：
 * 具体实现根据不同类型的资源返回不同的 Resource 类型，
 * 该方法供外部调用。
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public class DefaultResourceLoader implements ResourceLoader {

    /**
     * 根据资源 location 获取具体的资源类型
     * @param location 资源的 location
     * @return
     */
    @Override
    public Resource getResource(String location) {
        Assert.notNull(location, "Location must not be null");
        // classpath 类型
        if (location.startsWith(CLASSPATH_URL_PREFIX)) {
            // 返回 Resource 时把 CLASSPATH_URL_PREFIX 前缀去掉
            return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()));
        } else {
            try {
                // 远程资源类型，创建一个 URL，报异常就走下面
                URL url = new URL(location);
                return new UrlResource(url);
            } catch (MalformedURLException e) {
                // 文件系统类型（MalformedURLException 文件格式错误异常，说明不是 URL）
                return new FileSystemResource(location);
            }
        }
    }

}
