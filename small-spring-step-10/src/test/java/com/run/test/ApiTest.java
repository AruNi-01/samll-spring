package com.run.test;

import com.run.context.support.ClassPathXmlApplicationContext;
import com.run.test.bean.UserService;
import com.run.test.event.CustomEvent;
import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @desc: --add-opens java.base/java.lang=ALL-UNNAMED
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
@SpringBootTest(classes = ApiTest.class)
public class ApiTest {

    @Test
    public void test_event() {
        ClassPathXmlApplicationContext application = new ClassPathXmlApplicationContext("classpath:spring.xml");
        // 通过使用 applicationContext 新增加的发布事件接口方法，发布一个自定义事件 CustomEvent，让对应监听器监听，然后执行处理事件
        application.publishEvent(new CustomEvent(application, 20000001L, "我是自定义的事件"));
        application.registerShutdownHook();
    }


}
