package com.run.beans.factory.support;

import com.run.core.io.DefaultResourceLoader;
import com.run.core.io.ResourceLoader;

/**
 * @desc: BeanDefinitionReader 抽象类，把 BeanDefinitionReader 接口的前两个方法全部实现了，
 * 并提供了构造函数，让外部的使用者把 BeanDefinitionRegistry 传递进来。
 *
 * 在接口 BeanDefinitionReader 的具体实现类中，就可以通过 getRegistry() 方法获取 BeanDefinitionRegistry 接口，
 * 调用对应的注册方法把解析后的 XML 文件中的 Bean 信息，注册到 Spring 容器去了。
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
