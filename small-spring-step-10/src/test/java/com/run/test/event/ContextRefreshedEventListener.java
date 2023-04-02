package com.run.test.event;

import com.run.context.ApplicationListener;
import com.run.context.event.ContextRefreshedEvent;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/4/2
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println("上下文刷新事件：" + this.getClass().getName());
    }

}
