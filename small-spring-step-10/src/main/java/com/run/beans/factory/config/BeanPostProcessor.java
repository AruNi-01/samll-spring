package com.run.beans.factory.config;

import com.run.beans.BeansException;

/**
 * @desc: 该接口提供修改新的实例化 Bean 对象的扩展点，由用户实现
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;

}
