package com.run.context.event;

/**
 * @desc: 用于监听关闭动作
 * @author: AruNi_Lu
 * @date: 2023/4/1
 */
public class ContextClosedEvent extends ApplicationContextEvent {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public ContextClosedEvent(Object source) {
        super(source);
    }
}
