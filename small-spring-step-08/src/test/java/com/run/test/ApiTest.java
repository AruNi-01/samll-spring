package com.run.test;


import cn.hutool.core.io.IoUtil;
import com.run.beans.factory.support.DefaultListableBeanFactory;
import com.run.beans.factory.xml.XmlBeanDefinitionReader;
import com.run.context.ApplicationContext;
import com.run.context.support.ClassPathXmlApplicationContext;
import com.run.core.io.DefaultResourceLoader;
import com.run.core.io.Resource;
import com.run.test.bean.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * @desc: --add-opens java.base/java.lang=ALL-UNNAMED
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
@SpringBootTest(classes = ApiTest.class)
public class ApiTest {

    @Test
    public void test_InitAndDestroy() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        // 注册钩子
        applicationContext.registerShutdownHook();

        // 2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);
    }

}
