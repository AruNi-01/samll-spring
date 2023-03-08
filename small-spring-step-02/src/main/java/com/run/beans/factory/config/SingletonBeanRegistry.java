package com.run.beans.factory.config;

/**
 * @desc: 获取单例对象的 SingletonBeanRegistry 接口
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

}
