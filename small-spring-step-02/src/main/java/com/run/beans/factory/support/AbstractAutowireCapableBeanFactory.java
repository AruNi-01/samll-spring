package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;

/**
 * @desc: 用于实例化 bean 的类
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractAutowireCapableBeanFactory extends  AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        // 创建 bean 实例
        try {
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new com.run.beans.BeansException("Instantiation of bean failed", e);
        }
        // 创建后加入到单例容器缓存中
        addSingleton(beanName, bean);
        return bean;
    }

}
