package com.run.beans.factory.support;

import com.run.beans.BeansException;
import com.run.beans.factory.config.BeanDefinition;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;


import java.lang.reflect.Constructor;

/**
 * @desc: 通过 Cglib 方式实例化，需要先在 pom 文件中导入 cglib 依赖
 * @author: AruNi_Lu
 * @date: 2023/3/12
 */
public class CglibSubclassingInstantiationStrategy implements InstantiationStrategy{

    @Override
    public Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args) throws BeansException {
        // 增强类 Enhancer，它会根据某个给定的类创建子类，并且所有非 final 的方法都带有回调钩子。
        Enhancer enhancer = new Enhancer();
        // 为 BeanDefinition 类创建子类
        enhancer.setSuperclass(beanDefinition.getBeanClass());
        enhancer.setCallback(new NoOp() {
            // NoOp 回调相当简单，就是啥都不干的意思
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        });
        if (null == ctor) return enhancer.create();
        // 实例化有参对象，enhancer.create(Class[] argumentTypes, Object[] arguments);
        return enhancer.create(ctor.getParameterTypes(), args);
    }

}
