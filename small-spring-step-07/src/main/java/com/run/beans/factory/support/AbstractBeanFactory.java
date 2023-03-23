package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.beans.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 抽象的 bean 工厂类，定义模板方法：
 * 首先继承了 DefaultSingletonBeanRegistry，具备获取单例 bean 的方法注册添加单例 bean 的方法
 * 然后又实现了 BeanFactory，所以实现了它的 getBean 方法，给出了 getBean 的具体流程，
 * 但是真正的获取 bean 和创建 bean 不在此类实现，而是通过模板方法模式由不同的子类具体实现。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractBeanFactory
        extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    // 提供 BeanPostProcessor 容器
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    // 模板方法：获取 bean 的流程，具体的获取 BeanDefinition 和创建 bean 都由子类具体实现

    // 无参获取 Bean
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    // 有参获取 Bean
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    // 根据类型获取 Bean
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 抽取 doGetBean 方法：真正获取 Bean 的方法，带有参数
     * @param name Bean 的名字
     * @param args 具体参数
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        // 能从单例 bean 缓存中获取 bean 则可直接返回
        Object bean = getSingleton(name);
        if (bean != null) {
            return (T) bean;
        }

        // 创建 bean 并返回（先要获取 BeanDefinition）
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }


    // 下面两个为模板方法，由子类去实现：
    // 获取 bean 实例
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    // 创建 bean 实例，添加参数
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;


    // 提供向 BeanPostProcessor 容器中存取 BeanPostProcessor 的方法
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }
}
