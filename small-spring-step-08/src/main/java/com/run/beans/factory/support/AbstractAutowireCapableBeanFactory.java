package com.run.beans.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.run.beans.BeansException;
import com.run.beans.PropertyValue;
import com.run.beans.PropertyValues;
import com.run.beans.factory.*;
import com.run.beans.factory.config.AutowireCapableBeanFactory;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.beans.factory.config.BeanReference;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Watchable;

/**
 * @desc: 能够有能力自动装配 Bean 的抽象类，用于创建 bean 实例:
 * 继承了 AbstractBeanFactory 模板方法类，实现模板方法 createBean，实现了具体的创建 bean 的方法。
 * 创建 bean 后，除了放入 bean 容器中，还需要放入单例 bean 缓冲中，又 AbstractBeanFactory
 * 继承了 DefaultSingletonBeanRegistry，所以可以直接调用它的 addSingleton 方法。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public abstract class AbstractAutowireCapableBeanFactory
        extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    // 定义一个实例化策略对象，这里选择 Cglib 实现类
    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    // 重写模板方法 createBean，实现具体的创建 bean 流程
    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException {
        Object bean = null;
        try {
            // 创建 bean 实例，通过 createBeanInstance 创建，而不是直接通过 class 来 newInstance()
            bean = createBeanInstance(beanName, beanDefinition, args);

            // 给 Bean 填充属性
            applyPropertyValues(beanName, bean, beanDefinition);

            // 执行 Bean 的初始化方法，使用 BeanPostProcessor 的前置和后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Instantiation of bean failed", e);
        }

        // 注册有销毁方法的 Bean 对象
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

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

    /**
     * Bean 属性填充
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            if (propertyValues != null) {
                // 获取 beanDefinition.getPropertyValues() 循环进行属性填充操作
                for (PropertyValue propertyValue : propertyValues.getPropertyValues()) {
                    // 获取属性的名称和值
                    String name = propertyValue.getName();
                    Object value = propertyValue.getValue();

                    // 如果遇到 BeanReference，就需要递归调用 getBean 方法，获取 Bean 实例
                    if (value instanceof BeanReference) {
                        BeanReference beanReference = (BeanReference) value;
                        // 通过调用 getBean，先实例化需要依赖的对象（递归的方式）
                        value = getBean(beanReference.getBeanName());
                    }
                    // 当把依赖的 Bean 对象创建完成后，会递归回现在属性填充中

                    // 属性填充（通过使用 hutool 工具类中的方法，也可以使用反射进行属性填充）
                    BeanUtil.setFieldValue(bean, name, value);

                    //Class<?> clazz = bean.getClass();
                    //Field[] declaredFields = clazz.getDeclaredFields();
                    //for (Field declaredField : declaredFields) {
                    //
                    //}
                }
            }
        } catch (Exception e) {
            throw new BeansException("Error setting property values: " + beanName);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    // 初始化 Bean
    private Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 写入需要被感知的 BeanFactory、BeanName 等，后续可直接获取。
        // ApplicationContextAwareProcessor 已经在 refresh() 时被提前加入到 BeanPostProcessors 中了，
        // 在下面的 applyBeanPostProcessorsBeforeInitialization() 中会取出来进行写入
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getBeanClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // 1. 执行 BeanPostProcessor Before 处理（ApplicationContext 的写入在此方法中）
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        // 执行 Bean 对象的初始化方法
        try {
            invokeInitMethods(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }

        // 2. 执行 BeanPostProcessor After 处理
        wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        return wrappedBean;
    }

    // Bean 对象的初始化方法
    private void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception {
        // 方式一：实现了接口 InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        // 方式二：xml 文件中的 init-method
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            // 反射执行该方法
            initMethod.invoke(bean);
        }

    }

    /**
     * 注册有销毁方法的 Bean 对象，分别为实现了 DisposableBean 接口或者
     * 从 XML 中读取出来的 destroyMethodName 不为空的 Bean 对象。
     * 注意：这里传入的是适配器 DisposableBeanAdapter，再调用时直接调用适配器
     *      实现的 destroy() 方法即可，调用时不用考虑属于哪种方式。
     */
    private void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }

    // Bean 对象执行初始化前后的额外处理。
    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }
}
