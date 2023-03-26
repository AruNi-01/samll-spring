package com.run.context.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.context.ApplicationContext;
import com.run.context.ApplicationContextAware;

/**
 * @desc: ApplicationContextAware 的写入比较特殊（不像 BeanFactory/BeanName 等可以直接在创建 Bean 的时候获取到）：
 * 由于 ApplicationContext 的获取并不能直接在创建 Bean 时候就可以拿到，所以需要在 refresh 操作时，
 * 把 ApplicationContext 写入到一个包装的 BeanPostProcessor 中去，再由
 * AbstractAutowireCapableBeanFactory 中的 applyBeanPostProcessorsBeforeInitialization 方法调用。
 * @author: AruNi_Lu
 * @date: 2023/3/26
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware) {
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
