package com.run.beans.factory;


import com.run.beans.BeansException;

/**
 * @desc: Bean 工厂接口，提供 Bean 的获取方法 getBean(String name)，
 * 之后这个 Bean 工厂接口由抽象类 AbstractBeanFactory 实现。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    // 含有参数的 getBean 方法，方便实例化含有参数的 Bean 对象
    Object getBean(String name, Object... args) throws BeansException;

    // 根据类型获取 Bean
    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
