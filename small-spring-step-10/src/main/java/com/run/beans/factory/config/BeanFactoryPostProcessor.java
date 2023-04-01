package com.run.beans.factory.config;

import com.run.beans.BeansException;
import com.run.beans.factory.ConfigurableListableBeanFactory;

/**
 * @desc: 允许自定义修改 BeanDefinition 属性信息，提供 postProcessBeanFactory 方法，供用户实现。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
