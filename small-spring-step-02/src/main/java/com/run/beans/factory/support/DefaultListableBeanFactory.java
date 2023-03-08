package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 核心类：
 * 继承了 AbstractAutowireCapableBeanFactory 类，具备了接口 BeanFactory 和 AbstractBeanFactory 等一连串的功能实现。
 * 实现了接口 BeanDefinitionRegistry 中的 registerBeanDefinition() 方法，所以可以在此方便的注册 bean
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry {

    private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    protected BeanDefinition getBeanDefinition(String beanName) throws BeansException {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) throw new BeansException("No bean named '" + "' is defined");
        return beanDefinition;
    }

}
