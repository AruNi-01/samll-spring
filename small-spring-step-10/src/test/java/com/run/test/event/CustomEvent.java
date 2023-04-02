package com.run.test.event;

import com.run.context.ApplicationEvent;

/**
 * @desc: 创建一个自定义事件，在事件类的构造函数中可以添加自己的想要的入参信息。
 * 这个事件类最终会被完整的放到监听里，所以你添加的属性都会被获得到。
 * @author: AruNi_Lu
 * @date: 2023/4/2
 */
public class CustomEvent extends ApplicationEvent {

    private Long id;
    private String message;

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public CustomEvent(Object source, Long id, String message) {
        super(source);
        this.id = id;
        this.message = message;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}
