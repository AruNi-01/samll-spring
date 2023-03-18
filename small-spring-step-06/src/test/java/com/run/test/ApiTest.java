package com.run.test;


import cn.hutool.core.io.IoUtil;
import com.run.beans.factory.support.DefaultListableBeanFactory;
import com.run.beans.factory.xml.XmlBeanDefinitionReader;
import com.run.core.io.DefaultResourceLoader;
import com.run.core.io.Resource;
import com.run.test.bean.UserService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.InputStream;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
@SpringBootTest(classes = ApiTest.class)
public class ApiTest {

    // DefaultResourceLoader，用于获取资源
    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    // 下面三个方法分别测试 加载 ClassPath、FileSystem、URL 文件
    @Test
    public void test_classpath() throws IOException {
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        // 读取后可以从内容中搜索关键字；OLpj9823dZ
        Resource resource = resourceLoader.getResource("https://github.com/AruNi-01/small-spring/blob/main/small-spring-step-05/src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        // 1. 初始化 beanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件 & 注册 Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取 Bean 对象调用方法
        UserService userService = (UserService) beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

}
