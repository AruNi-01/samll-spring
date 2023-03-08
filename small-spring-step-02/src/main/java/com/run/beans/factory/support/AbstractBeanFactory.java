package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.BeanFactory;
import com.run.beans.factory.config.BeanDefinition;

/**
 * @desc: 抽象类定义模板方法：
 * 首先继承了 DefaultSingletonBeanRegistry，具备使用单例注册 bean 的方法
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry implements BeanFactory {

    // 模板方法：获取 bean 的流程，具体的创建 bean 由子类实现
    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null) return bean;

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }


    // 下面两个为模板方法，由子类去实现：
    // 获取 bean 实例
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    // 创建 bean 实例
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;

}
