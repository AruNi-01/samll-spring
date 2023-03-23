package com.run.test.common;

import com.run.beans.BeansException;
import com.run.beans.PropertyValue;
import com.run.beans.PropertyValues;
import com.run.beans.factory.BeanFactory;
import com.run.beans.factory.ConfigurableListableBeanFactory;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @desc: 对实例化过程中的 Bean 对象做一些操作
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
