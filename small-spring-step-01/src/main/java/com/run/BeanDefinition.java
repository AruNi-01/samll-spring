package com.run;

/**
 * @desc: Bean 定义
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class BeanDefinition {

    // Bean 对象
    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }

}
