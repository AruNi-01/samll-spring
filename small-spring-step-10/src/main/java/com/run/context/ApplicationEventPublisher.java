package com.run.context;

/**
 * @desc: 整个事件的发布接口，所有的事件都需要从这个接口发布出去，发布方法在 AbstractApplicationContext 中
 * 实现，具体就是调用广播器 SimpleApplicationEventMulticaster 执行 multicastEvent() 进行广播发送
 * @author: AruNi_Lu
 * @date: 2023/4/1
 */
public interface ApplicationEventPublisher {

    /**
     * 发布事件
     */
    void publishEvent(ApplicationEvent event);

}
