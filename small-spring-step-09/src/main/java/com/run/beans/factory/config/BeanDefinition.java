package com.run.beans.factory.config;

import com.run.beans.PropertyValues;

/**
 * @desc: bean 的定义，定义成 Class 类型，而不是 Object。这样
 * 可以把 bean 的实例化操作放到容器中处理，而不是在初始化阶段自己 new。
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class BeanDefinition {

    String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

    String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

    /**
     * Bean 类对象
     */
    private Class beanClass;

    /**
     * 依赖的属性集合
     */
    private PropertyValues propertyValues;

    /**
     * 从 xml 文件的 Bean 对象中获取 initMethodName
     */
    private String initMethodName;

    /**
     * 从 xml 文件的 Bean 对象中获取 destroyMethodName
     */
    private String destroyMethodName;

    /**
     * Bean 对象的作用域
     */
    private String scope = SCOPE_SINGLETON;

    private boolean singleton = true;
    private boolean prototype = false;


    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
    }

    // 在 xml 注册 Bean 定义时，通过 scope 字段来判断是单例还是原型
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    public String getScope() {
        return scope;
    }

    public boolean isSingleton() {
        return singleton;
    }

    public boolean isPrototype() {
        return prototype;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public String getInitMethodName() {
        return initMethodName;
    }

    public String getDestroyMethodName() {
        return destroyMethodName;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }

    public void setInitMethodName(String initMethodName) {
        this.initMethodName = initMethodName;
    }

    public void setDestroyMethodName(String destroyMethodName) {
        this.destroyMethodName = destroyMethodName;
    }
}
