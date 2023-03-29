package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.FactoryBean;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @desc: 关于 FactoryBean 此类对象的注册/获取操作
 * @author: AruNi_Lu
 * @date: 2023/3/28
 */
public abstract class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    /**
     * FactoryBean 创建的单例对象的缓存: FactoryBean name -> object
     */
    private final Map<String, Object> factoryObjectCache = new ConcurrentHashMap<>();

    /**
     * 从缓存中获取 FactoryBean 类对象
     */
    protected Object getCachedObjectForFactoryBean(String beanName) {
        Object object = this.factoryObjectCache.get(beanName);
        // NULL_OBJECT 为内部空单例对象的标志，不是 null，而是 new Object()
        return (object != NULL_OBJECT ? object : null);
    }

    /**
     * 获取 FactoryBean 类对象的方法，如果是单例则还需要像缓存策略一样，先查出来在写入缓存
     */
    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName) {
        if (factoryBean.isSingleton()) {
            Object object = this.factoryObjectCache.get(beanName);
            if (object == null) {
                object = doGetObjectFromFactoryBean(factoryBean, beanName);
                this.factoryObjectCache.put(beanName, (object != null ? object : NULL_OBJECT));
            }
            return (object != NULL_OBJECT ? object : null);
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    /**
     * 通过 FactoryBean#getObject() 方法获取该 FactoryBean
     */
    private Object doGetObjectFromFactoryBean(final FactoryBean factoryBean, final String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e) {
            throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
        }
    }

}
