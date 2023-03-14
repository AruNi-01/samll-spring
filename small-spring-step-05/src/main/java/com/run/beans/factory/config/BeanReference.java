package com.run.beans.factory.config;

/**
 * @desc: Bean 的引用，只用于存储对象类型的 Bean 依赖
 * @author: AruNi_Lu
 * @date: 2023/3/13
 */
public class BeanReference {

    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }

}
