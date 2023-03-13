package com.run.beans.factory.support;

import com.run.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 默认的单例 bean 注册表，其中提供了一个单例容器缓存，用于快速获取单例 bean，
 * 实现 SingletonBeanRegistry 接口，实现 getSingleton 方法获取单例 bean，
 * 同时提供一个 addSingleton 方法来添加单例 bean，供子类调用。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    // 单例容器缓存
    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
