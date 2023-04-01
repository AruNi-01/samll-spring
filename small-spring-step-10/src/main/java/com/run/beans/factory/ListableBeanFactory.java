package com.run.beans.factory;

import com.run.beans.BeansException;

import java.util.Map;

/**
 * @desc: 扩展 BeanFactory 的接口
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface ListableBeanFactory extends BeanFactory {

    /**
     * 按照类的类型返回所有 Bean 实例
     * @param type
     * @param <T>
     * @return
     */
    <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException;

    /**
     * 返回 BeanDefinition 注册表 Registry 中所有的 Bean 名称
     */
    String[] getBeanDefinitionNames();

}
