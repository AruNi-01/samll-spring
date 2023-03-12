package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @desc: 能够有能力自动装配 Bean 的抽象类，用于创建 bean 实例:
 * 继承了 AbstractBeanFactory 模板方法类，实现模板方法 createBean，实现了具体的创建 bean 的方法。
 * 创建 bean 后，除了放入 bean 容器中，还需要放入单例 bean 缓冲中，又 AbstractBeanFactory
 * 继承了 DefaultSingletonBeanRegistry，所以可以直接调用它的 addSingleton 方法。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    // 定义一个实例化策略对象，这里选择 Cglib 实现类
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    // 重写模板方法 createBean，实现具体的创建 bean 流程
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 创建 bean 实例，通过 createBeanInstance 创建，而不是直接通过 class 来 newInstance()
            bean = createBeanInstance(beanName, beanDefinition, args);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 创建后加入到单例容器缓存中
        addSingleton(beanName, bean);
        return bean;
    }

    /**
     * 新增 createBeanInstance 方法：具体创建 Bean 实例的方法
     */
    protected Object createBeanInstance(String beanName, BeanDefinition beanDefinition, Object[] args) {
        Constructor constructorToUse = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        // 获取到所有的构造函数
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        // 循环对比入参信息和构造函数的匹配情况，获取对应的构造函数。这里简单的比较参数数量，
        // 实际 Spring 源码中还比较了类型，否则相同数量不同类型的入参就会出问题。
        for (Constructor<?> ctor : declaredConstructors) {
            if (null != args && ctor.getParameterTypes().length == args.length) {
                constructorToUse = ctor;
                break;
            }
        }
        // 实例化对象，将对应的构造函数传入
        return getInstantiationStrategy().instantiate(beanDefinition, beanName, constructorToUse, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

}
