package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;

/**
 * @desc: 能够有能力自动装配 Bean 的抽象类，用于创建 bean 实例:
 * 继承了 AbstractBeanFactory 模板方法类，实现模板方法 createBean，实现了具体的创建 bean 的方法。
 * 创建 bean 后，除了放入 bean 容器中，还需要放入单例 bean 缓冲中，又 AbstractBeanFactory
 * 继承了 DefaultSingletonBeanRegistry，所以可以直接调用它的 addSingleton 方法。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractAutowireCapableBeanFactory extends  AbstractBeanFactory {

    // 重写模板方法 createBean，实现具体的创建 bean 流程
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
