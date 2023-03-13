package com.run.test;


import com.run.beans.PropertyValue;
import com.run.beans.PropertyValues;
import com.run.beans.factory.BeanFactory;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.BeanReference;
import com.run.beans.factory.support.DefaultListableBeanFactory;
import com.run.test.bean.UserDao;
import com.run.test.bean.UserService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.util.prefs.BackingStoreException;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
@SpringBootTest(classes = ApiTest.class)
public class ApiTest {

    // 测试
    @Test
    public void test_BeanFactory() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. UserDao 注入 bean 容器
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3. 为 UserService 设置依赖的属性 [uId, userDao]，其中 userDao 又是一个 bean 对象
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("uId", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4. UserService 注入 bean 容器，将它依赖的属性进行填充
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5. UserService 获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

}
