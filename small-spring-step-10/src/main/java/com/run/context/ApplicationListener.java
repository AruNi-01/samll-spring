package com.run.context;

import java.util.EventListener;

/**
 * @desc: 由 ApplicationEvent 事件监听器实现的接口，基于标准的 java.util.EventListener 接口，用于观察者设计模式。
 * @author: AruNi_Lu
 * @date: 2023/4/1
 */
public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    /**
     * 处理 ApplicationEvent 事件，具体处理逻辑要用户自定义
     * @param event 要响应的事件
     */
    void onApplicationEvent(E event);

}
