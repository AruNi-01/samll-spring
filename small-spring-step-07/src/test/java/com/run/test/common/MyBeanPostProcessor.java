package com.run.test.common;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanPostProcessor;
import com.run.test.bean.UserService;

/**
 * @desc: 对实例化过程中的 Bean 对象做一些操作。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
