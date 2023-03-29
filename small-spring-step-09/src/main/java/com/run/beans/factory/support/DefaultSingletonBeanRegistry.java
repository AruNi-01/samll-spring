package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.DisposableBean;
import com.run.beans.factory.config.SingletonBeanRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 默认的单例 bean 注册表，其中提供了一个单例容器缓存，用于快速获取单例 bean，
 * 实现 SingletonBeanRegistry 接口，实现 getSingleton 方法获取单例 bean，
 * 同时提供一个 addSingleton 方法来添加单例 bean，供子类调用。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * 内部空单例对象的标志: 用作并发映射的标记值(不支持空值)。
     */
    protected static final Object NULL_OBJECT = new Object();

    // 单例容器缓存
    private Map<String, Object> singletonObjects = new HashMap<>();

    // 用于保存有销毁方法的 Bean 容器
    private Map<String, DisposableBean> disposableBeans = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeans.put(beanName, bean);
    }

    public void destroySingletons() {
        Object[] disposableBeanNames = this.disposableBeans.keySet().toArray();
        for (int i = disposableBeanNames.length - 1; i >= 0; i--) {
            Object beanName = disposableBeanNames[i];
            DisposableBean disposableBean = disposableBeans.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
