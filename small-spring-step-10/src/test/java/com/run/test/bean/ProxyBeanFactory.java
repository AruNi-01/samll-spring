package com.run.test.bean;

import com.run.beans.factory.FactoryBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @desc: 实现接口 FactoryBean 的代理类 ProxyBeanFactory，模拟了 UserDao 的原有功能，类似于 MyBatis 框架中的代理操作
 * @author: AruNi_Lu
 * @date: 2023/3/28
 */
public class ProxyBeanFactory implements FactoryBean<IUserDao> {
    @Override
    public IUserDao getObject() throws Exception {
        // InvocationHandler 的代理对象，当有方法调用的时候，则执行代理对象的功能。
        InvocationHandler handler = (proxy, method, args) -> {

            Map<String, String> map = new HashMap<>();
            map.put("10001", "孙悟空");
            map.put("10002", "猪八戒");
            map.put("10003", "沙悟净");

            return "你被代理了 " + method.getName() + ": " + map.get(args[0].toString());
        };
        return (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{IUserDao.class}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return IUserDao.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
