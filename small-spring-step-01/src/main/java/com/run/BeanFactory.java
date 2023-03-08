package com.run;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @desc: Bean 工厂
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class BeanFactory {

    // Map 用于存储 Bean 对象
    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    // 将 Bean 注册到 Map 容器中
    public void registerBeanDefinition(String name, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(name, beanDefinition);
    }

    // 从 Map 容器中获取 Bean
    public Object getBean(String name) {
        return beanDefinitionMap.get(name).getBean();
    }

}
