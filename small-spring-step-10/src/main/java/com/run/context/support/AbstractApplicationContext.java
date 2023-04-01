package com.run.context.support;

import com.run.beans.BeansException;
import com.run.beans.factory.ConfigurableListableBeanFactory;
import com.run.beans.factory.config.BeanFactoryPostProcessor;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.beans.factory.config.ConfigurableBeanFactory;
import com.run.context.ConfigurableApplicationContext;
import com.run.core.io.DefaultResourceLoader;

import java.util.Map;

/**
 * @desc: 应用上下文抽象类：
 * 继承 DefaultResourceLoader 是为了处理 spring.xml 配置资源的加载。
 *
 * 实现 ConfigurableApplicationContext，在 refresh() 定义实现过程，包括：
 *      1. 创建 BeanFactory，并加载 BeanDefinition
 *      2. 获取 BeanFactory
 *      3. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
 *      4. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
 *      5.  提前实例化单例Bean对象
 *
 * 另外提供 refreshBeanFactory()、getBeanFactory() 抽象方法，由后面的继承此抽象类的其他抽象类实现。其实就是模板方法模式的运用。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public abstract class AbstractApplicationContext
        extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();

        // 3. 添加 ApplicationContextAwareProcessor，让继承自 ApplicationContextAware 的 Bean 对象都能感知所属的 ApplicationContext
        beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 在 Bean 实例化之前，执行 BeanFactoryPostProcessor (Invoke factory processors registered as beans in the context.)
        invokeBeanFactoryPostProcessors(beanFactory);

        // 5. BeanPostProcessor 需要提前于其他 Bean 对象实例化之前执行注册操作
        registerBeanPostProcessors(beanFactory);

        // 6. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        getBeanFactory().destroySingletons();
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

}
