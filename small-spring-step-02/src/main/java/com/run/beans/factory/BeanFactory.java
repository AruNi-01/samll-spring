package com.run.beans.factory;

import org.springframework.beans.BeansException;

/**
 * @desc: Bean 工厂，提供 Bean 的获取方法 getBean(String name)，
 * 之后这个 Bean 工厂接口由抽象类 AbstractBeanFactory 实现
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

}
