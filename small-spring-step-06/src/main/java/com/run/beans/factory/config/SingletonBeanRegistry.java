package com.run.beans.factory.config;

/**
 * @desc: 单例 bean 注册表接口，提供一个获取单例对象的方法，供实现类实现
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

}
