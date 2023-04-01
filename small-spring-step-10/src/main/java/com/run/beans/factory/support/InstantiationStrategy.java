package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @desc: 实例化策略接口
 * @author: AruNi_Lu
 * @date: 2023/3/12
 */
public interface InstantiationStrategy {

    /**
     * 实例化接口 instantiate 方法：
     * @param ctor 包含一些必要的类信息，用来拿到符合入参信息对应的构造函数
     * @param args 具体的入参信息
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException;

}
