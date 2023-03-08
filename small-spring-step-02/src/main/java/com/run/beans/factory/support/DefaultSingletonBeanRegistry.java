package com.run.beans.factory.support;

import com.run.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 实现 SingletonBeanRegistry 接口，实现 getSingleton 方法
 * 同时提供一个 addSingleton 方法，供子类调用
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
