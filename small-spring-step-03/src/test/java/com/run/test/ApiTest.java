package com.run.test;


import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.support.DefaultListableBeanFactory;
import com.run.beans.factory.support.SimpleInstantiationStrategy;
import com.run.test.bean.UserService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.NoOp;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
        //beanFactory.setInstantiationStrategy(new SimpleInstantiationStrategy());

        // 2. 注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 3. 带有参数的获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService", "AruNi");
        userService.queryUserInfo();
    }


    // -------------------------- 下面是几种不同的实例化操作 --------------------------------
    // 无参实例化
    @Test
    public void test_newInstance() throws InstantiationException, IllegalAccessException {
        UserService userService = UserService.class.newInstance();
        System.out.println(userService);
    }

    // 有参实例化，反射
    @Test
    public void test_constructor() throws Exception {
        Class<UserService> userServiceClass = UserService.class;
        // 通过参数类型得到一个构造器
        Constructor<UserService> declareConstructor = userServiceClass.getDeclaredConstructor(String.class);
        UserService userService = declareConstructor.newInstance("AruNi");
        System.out.println(userService);
    }

    // 获取构造函数的信息进行实例化
    @Test
    public void test_parameterTypes() throws Exception{
        Class<UserService> beanClass = UserService.class;
        // 获取所有构造器
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();
        // UserService 有两个构造器，第一个是有参，第二个是无参。我们获取有参构造器
        // constructor.getParameterTypes()：获取该有参构造器的参数类型（String）
        Constructor<?> constructor = declaredConstructors[0];
        Constructor<UserService> declaredConstructor = beanClass.getDeclaredConstructor(constructor.getParameterTypes());
        UserService userService = declaredConstructor.newInstance("AruNi");
        System.out.println(userService);
    }

    // cglib 实例化
    @Test
    public void test_cglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new NoOp() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        // enhancer.create(参数类型, 参数值);
        Object obj = enhancer.create(new Class[]{String.class}, new Object[]{"AruNi"});
        System.out.println(obj);
    }
}
