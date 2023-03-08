package com.run.beans.factory.support;

import com.run.beans.factory.config.BeanDefinition;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public interface BeanDefinitionRegistry {

    // 向注册表中注册 bean
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
