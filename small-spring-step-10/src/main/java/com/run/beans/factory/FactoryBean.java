package com.run.beans.factory;

/**
 * @desc: FactoryBean 接口，表示复杂的 Bean 对象
 * @author: AruNi_Lu
 * @date: 2023/3/28
 */
public interface FactoryBean<T> {

    T getObject() throws Exception;

    Class<?> getObjectType();

    boolean isSingleton();
}
