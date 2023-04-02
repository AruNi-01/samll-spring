package com.run.context.support;

import com.run.beans.BeansException;
import com.run.beans.factory.ConfigurableListableBeanFactory;
import com.run.beans.factory.config.BeanFactoryPostProcessor;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.beans.factory.config.ConfigurableBeanFactory;
import com.run.context.ApplicationEvent;
import com.run.context.ApplicationListener;
import com.run.context.ConfigurableApplicationContext;
import com.run.context.event.ApplicationEventMulticaster;
import com.run.context.event.ContextClosedEvent;
import com.run.context.event.ContextRefreshedEvent;
import com.run.context.event.SimpleApplicationEventMulticaster;
import com.run.core.io.DefaultResourceLoader;

import java.util.Collection;
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

    public static final String APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

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

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8. 提前实例化单例 Bean 对象
        beanFactory.preInstantiateSingletons();

        // 9. 完成刷新后，发布刷新完成事件
        finishRefresh();

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

    /**
     * 初始化事件发布者，即实例化 ApplicationEventMulticaster，使 ApplicationContext 具备
     * SimpleApplicationEventMulticaster 的功能。然后将实例化的 Bean 对象 applicationEventMulticaster
     * 注册进单例容器，供其他地方使用。
     */
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICASTER_BEAN_NAME, applicationEventMulticaster);
    }

    /**
     * 注册事件监听器：
     * 1. 先获取继承/实现了 ApplicationListener 接口的所有监听器
     * 2. 将这些监听器加入到监听器容器中
     */
    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    /**
     * 完成刷新后，发布刷新完成事件
     */
    private void finishRefresh() {
        publishEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {
        // 发布容器关闭事件
        publishEvent(new ContextClosedEvent(this));

        // 执行销毁单例 bean 的销毁方法
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
