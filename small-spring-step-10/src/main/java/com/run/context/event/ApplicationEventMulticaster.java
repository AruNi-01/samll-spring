package com.run.context.event;

import com.run.context.ApplicationEvent;
import com.run.context.ApplicationListener;


/**
 * @desc: 事件广播器接口
 * @author: AruNi_Lu
 * @date: 2023/4/1
 */
public interface ApplicationEventMulticaster {

    /**
     * 添加一个监听器
     */
    void addApplicationListener(ApplicationListener<?> listener);

    /**
     * 删除一个监听器
     */
    void removeApplicationListener(ApplicationListener<?> listener);

    /**
     * 将给定的 ApplicationEvent 事件多播给合适的监听器
     */
    void multicastEvent(ApplicationEvent event);

}
