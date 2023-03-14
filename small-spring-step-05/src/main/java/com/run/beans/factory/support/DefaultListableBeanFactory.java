package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 核心类，提供一个 BeanDefinition 容器来存储 bean，同时暴露一些方法供外部使用：
 * 1. 实现了接口 BeanDefinitionRegistry 的 registerBeanDefinition() 方法，提供具体注册 bean 的功能。
 * 2. 继承了 AbstractAutowireCapableBeanFactory，因此也具有重写 AbstractBeanFactory 的模板方法的能力，
 *    此模板方法中还有一个 getBeanDefinition()，就在此有了具体实现。
 * 并且该类是继承链的最底层，所以具有所有父类或接口的方法，比如：AbstractBeanFactory 类的 getBean() 方法。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class DefaultListableBeanFactory
        extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    // 提供 bean 容器
    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    // 提供具体注册 bean 的方法
    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return beanDefinitionMap.containsKey(beanName);
    }

    // 提供具体获取 bean 的方法
    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + beanName + "' is defined");
        return beanDefinition;
    }

}
