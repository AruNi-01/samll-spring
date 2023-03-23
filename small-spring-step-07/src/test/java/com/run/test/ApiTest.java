package com.run.test;


import cn.hutool.core.io.IoUtil;
import com.run.beans.factory.support.DefaultListableBeanFactory;
import com.run.beans.factory.xml.XmlBeanDefinitionReader;
import com.run.context.support.ClassPathXmlApplicationContext;
import com.run.core.io.DefaultResourceLoader;
import com.run.core.io.Resource;
import com.run.test.bean.UserService;
import com.run.test.common.MyBeanFactoryPostProcessor;
import com.run.test.common.MyBeanPostProcessor;
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

    // 不使用应用上下文
    @Test
    public void test_BeanFactoryPostProcessorAndBeanPostProcessor() {
        // 1. 初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件 & 注册 Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. BeanDefinition 加载完成 & Bean 实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean 实例化之后，修改 Bean 的属性信息
        MyBeanPostProcessor beanPostProcessor = new MyBeanPostProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        // 5. 获取 Bean 对象，调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

        // 测试结果：孙悟空，公司：改为：字节跳动，地点：改为：北京
    }

    // 使用应用上下文：再操作起来就方便多了，这才是面向用户使用的类，在这里可以一步把配置文件交给
    // ClassPathXmlApplicationContext，也不需要管理一些自定义实现的 Spring 接口的类
    @Test
    public void test_xml() {
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

        // 2. 获取 Bean 对象调用方法
        UserService userService = applicationContext.getBean("userService", UserService.class);
        String result = userService.queryUserInfo();
        System.out.println("测试结果：" + result);

        // 测试结果：孙悟空，公司：改为：字节跳动，地点：改为：北京
    }

}
