package com.run.beans.factory;

import com.run.beans.BeansException;
import com.run.beans.factory.config.AutowireCapableBeanFactory;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.ConfigurableBeanFactory;

/**
 * @desc: 除了 {@link ConfigurableBeanFactory}之外，
 * 该接口还提供了分析和修改 bean 定义，并预实例化单例的方法。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface ConfigurableListableBeanFactory
        extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

}
