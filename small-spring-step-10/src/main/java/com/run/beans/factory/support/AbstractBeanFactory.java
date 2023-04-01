package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.FactoryBean;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.beans.factory.config.ConfigurableBeanFactory;
import com.run.utils.ClassUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc: 抽象的 bean 工厂类，定义模板方法：
 *
 * @old 首先继承了 DefaultSingletonBeanRegistry，具备获取单例 bean 的方法注册添加单例 bean 的方法。
 *
 * @new 把 AbstractBeanFactory 原来继承的 DefaultSingletonBeanRegistry，修改为继承 FactoryBeanRegistrySupport。
 * 因为需要扩展出创建 FactoryBean 对象的能力
 *
 * 然后又实现了 BeanFactory，所以实现了它的 getBean 方法，给出了 getBean 的具体流程，
 * 但是真正的获取 bean 和创建 bean 不在此类实现，而是通过模板方法模式由不同的子类具体实现。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractBeanFactory
        extends FactoryBeanRegistrySupport implements ConfigurableBeanFactory {

    // 提供 BeanPostProcessor 容器
    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    private ClassLoader beanClassLoader = ClassUtil.getDefaultClassLoader();

    // 模板方法：获取 bean 的流程，具体的获取 BeanDefinition 和创建 bean 都由子类具体实现

    // 无参获取 Bean
    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name, null);
    }

    // 有参获取 Bean
    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    // 根据类型获取 Bean
    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * 抽取 doGetBean 方法：真正获取 Bean 的方法，带有参数
     * @param name Bean 的名字
     * @param args 具体参数
     */
    protected <T> T doGetBean(final String name, final Object[] args) {
        Object sharedInstance = getSingleton(name);
        // 能从单例 bean 缓存中获取 bean 则可直接返回
        if (sharedInstance != null) {
            // 如果是 FactoryBean，则需要调用 FactoryBean#getObject()
            return (T) getObjectForBeanInstance(sharedInstance, name);
        }

        // 创建 bean 并返回（先要获取 BeanDefinition）
        BeanDefinition beanDefinition = getBeanDefinition(name);
        Object bean = createBean(name, beanDefinition, args);

        // 如果是 FactoryBean，则需要调用 FactoryBean#getObject()
        return (T) getObjectForBeanInstance(bean, name);
    }

    /**
     * 抽取出一个方法来判断是否是 FactoryBean，然后再返回对应的 Bean 对象
     * @param beanInstance
     * @param beanName
     * @return
     */
    private Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        // 如果不是 FactoryBean，直接返回即可
        if (!(beanInstance instanceof FactoryBean)) {
            return beanInstance;
        }

        // 先从 FactoryBean 缓存中获取（包括单例的缓存为空，以及原型模式）
        Object object = getCachedObjectForFactoryBean(beanName);

        // 如果缓存为空，则再调用 getObjectFromFactoryBean() 获取
        if (object == null) {
            FactoryBean<?> factoryBean = (FactoryBean<?>) beanInstance;
            object = getObjectFromFactoryBean(factoryBean, beanName);
        }

        return object;
    }


    // 下面两个为模板方法，由子类去实现：
    // 获取 bean 实例
    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    // 创建 bean 实例，添加参数
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException;


    // 提供向 BeanPostProcessor 容器中存取 BeanPostProcessor 的方法
    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return this.beanPostProcessors;
    }

    public ClassLoader getBeanClassLoader() {
        return this.beanClassLoader;
    }
}
