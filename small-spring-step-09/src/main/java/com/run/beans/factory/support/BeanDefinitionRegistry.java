package com.run.beans.factory.support;

import com.run.beans.factory.config.BeanDefinition;

/**
 * @desc: BeanDefinitionRegistry 接口：提供注册 BeanDefinition 的方法
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public interface BeanDefinitionRegistry {

    // 向容器中注册 bean
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    // 判断是否包含指定名称的 BeanDefinition
    boolean containsBeanDefinition(String beanName);

}
