package com.run.beans.factory.config;

/**
 * @desc: bean 的定义，定义成 Class 类型，而不是 Object。这样
 * 可以把 bean 的实例化操作放到容器中处理，而不是在初始化阶段自己 new。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class BeanDefinition {

    private Class beanClass;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

}
