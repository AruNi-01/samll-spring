package com.run.test.event;

import com.run.context.ApplicationListener;
import com.run.context.event.ContextClosedEvent;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/4/2
 */
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("上下文关闭事件：" + this.getClass().getName());
    }
}
