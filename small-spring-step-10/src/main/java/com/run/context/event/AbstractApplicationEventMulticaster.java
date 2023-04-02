package com.run.context.event;

import com.run.beans.BeansException;
import com.run.beans.factory.BeanFactory;
import com.run.beans.factory.BeanFactoryAware;
import com.run.context.ApplicationEvent;
import com.run.context.ApplicationListener;
import com.run.utils.ClassUtil;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @desc: 实现 ApplicationEventMulticaster 接口，在这个类中可以实现一些基本功能，避免所有直接实现接口的类还需要处理细节
 * @author: AruNi_Lu
 * @date: 2023/4/1
 */
public abstract class AbstractApplicationEventMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    // 提供一个存储应用监听器的容器
    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 摘取符合广播事件的所有监听器，具体过滤动作在 supportsEvent 方法中
     * @param event 事件
     * @return 符合广播事件的所有监听器
     */
    protected Collection<ApplicationListener> getApplicationListeners(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener, event)) allListeners.add(listener);
        }
        return allListeners;
    }

    /**
     * 判断监听器是否对该事件感兴趣
     * @param applicationListener 监听器
     * @param event 事件
     * @return 监听器是否对该事件感兴趣，是返回 true
     */
    protected boolean supportsEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // // 按照 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化策略，需要判断后获取目标 class
        Class<?> targetClass = ClassUtil.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;
        Type genericInterface = targetClass.getGenericInterfaces()[0];

        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("wrong event class name: " + className);
        }
        // event.getClass() 类是否继承/实现自 eventClassName
        return eventClassName.isAssignableFrom(event.getClass());
    }

}
