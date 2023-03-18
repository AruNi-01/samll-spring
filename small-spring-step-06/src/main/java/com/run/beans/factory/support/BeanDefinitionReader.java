package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.core.io.Resource;
import com.run.core.io.ResourceLoader;

/**
 * @desc: BeanDefinitionReader 的接口，提供一些加载 BeanDefinition 的方法。
 * 注意：getRegistry()、getResourceLoader()，都是用于提供给后面三个方法
 * 的工具，加载和注册，这两个方法的实现会包装到抽象类中，以免污染具体的接口实现方法。
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public interface BeanDefinitionReader {

    // 获取 BeanDefinitionRegistry 接口
    BeanDefinitionRegistry getRegistry();

    // 获取 ResourceLoader 接口
    ResourceLoader getResourceLoader();

    // 三个加载Bean定义的方法
    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

}
