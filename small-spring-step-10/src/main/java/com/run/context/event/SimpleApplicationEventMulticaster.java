package com.run.context.event;

import com.run.beans.factory.BeanFactory;
import com.run.context.ApplicationEvent;
import com.run.context.ApplicationListener;

/**
 * @desc: AbstractApplicationEventMulticaster 的简单实现类：
 * 1. 为父类提供 beanFactory
 * 2. 实现 multicastEvent() 方法
 * @author: AruNi_Lu
 * @date: 2023/4/2
 */
public class SimpleApplicationEventMulticaster extends AbstractApplicationEventMulticaster {

    // 通过依赖注入，将 beanFactory 注入进来，然后通过 setBeanFactory() 提供给父类
    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @SuppressWarnings("uncheckd")
    @Override
    public void multicastEvent(final ApplicationEvent event) {
        // 获取该事件的监听者，执行 onApplicationEvent() 处理事件
        for (final ApplicationListener listener : getApplicationListeners(event)) {
            listener.onApplicationEvent(event);
        }
    }
}
