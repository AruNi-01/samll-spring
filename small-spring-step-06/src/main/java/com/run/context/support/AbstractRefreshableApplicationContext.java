package com.run.context.support;

import com.run.beans.BeansException;
import com.run.beans.factory.ConfigurableListableBeanFactory;
import com.run.beans.factory.support.DefaultListableBeanFactory;

/**
 * @desc: 获取 Bean工厂和加载资源
 * 在 refreshBeanFactory() 中主要是获取了 DefaultListableBeanFactory 的实例化，以及对资源配置的加载操作 loadBeanDefinitions(beanFactory),
 * 在加载完成后即可完成对 spring.xml 配置文件中 Bean 对象的定义和注册，同时也包括实现了接口 BeanFactoryPostProcessor、BeanPostProcessor 的配置 Bean 信息.
 * 但此时资源加载还只是定义了一个抽象类方法 loadBeanDefinitions()，继续由其他抽象类继承实现。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinition(beanFactory);
        this.beanFactory = beanFactory;
    }

    protected abstract void loadBeanDefinition(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }
}
