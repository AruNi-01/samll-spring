package com.run.context.event;

import com.run.context.ApplicationContext;
import com.run.context.ApplicationEvent;

/**
 * @desc: 定义事件的类，所有的事件（包括关闭、刷新或自己实现的事件）都需要继承这个类
 * @author: AruNi_Lu
 * @date: 2023/4/1
 */
public class ApplicationContextEvent extends ApplicationEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ApplicationContextEvent(Object source) {
        super(source);
    }

    /**
     * 获取引发事件的 ApplicationContext
     */
    public final ApplicationContext getApplicationContext() {
        return (ApplicationContext) getSource();
    }
}
