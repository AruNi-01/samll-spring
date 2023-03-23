package com.run.beans.factory.config;

import com.run.beans.factory.HierarchicalBeanFactory;

/**
 * @desc: BeanFactory 配置接口
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

    /**
     * 向 BeanPostProcessor 容器中添加执行器
     * @param beanPostProcessor
     */
    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
