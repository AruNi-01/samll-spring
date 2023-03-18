package com.run.beans.factory.config;

import com.run.beans.PropertyValues;

/**
 * @desc: bean 的定义，定义成 Class 类型，而不是 Object。这样
 * 可以把 bean 的实例化操作放到容器中处理，而不是在初始化阶段自己 new。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class BeanDefinition {

    /**
     * Bean 类对象
     */
    private Class beanClass;

    /**
     * 依赖的属性集合
     */
    private PropertyValues propertyValues;

    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
