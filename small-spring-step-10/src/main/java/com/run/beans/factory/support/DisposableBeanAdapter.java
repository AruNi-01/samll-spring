package com.run.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.run.beans.BeansException;
import com.run.beans.factory.DisposableBean;
import com.run.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @desc: 销毁方法适配器 (接口和配置)
 * 销毁方法有两种甚至多种方式，目前有实现接口 DisposableBean、配置信息 destroy-method，两种方式。
 * 而这两种方式的销毁动作是由 AbstractApplicationContext 在注册虚拟机钩子后，虚拟机关闭前执行的操作动作。
 * 那么在销毁执行时不太希望还得关注都销毁哪些类型的方法，它的使用上更希望是有一个统一的接口进行销毁，所以这里就新增了适配类，做统一处理。
 * @author: AruNi_Lu
 * @date: 2023/3/23
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 方式一：实现了接口 DisposableBean
        if (bean instanceof DisposableBean) {
            // 调用实现类的 destroy() 方法
            ((DisposableBean) bean).destroy();
        }

        // 方式二：xml 文件中的 destroy-method，判断是为了避免二次执行销毁
        if (StrUtil.isNotEmpty(destroyMethodName) &&
                !(bean instanceof DisposableBean && "destroy".equals(this.destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Could not find a destroy method named '" + destroyMethod + "' on bean with name '" + beanName + "'");
            }
            // 调用 XML 配置中对应的 destroyMethod
            destroyMethod.invoke(bean);
        }
    }
}
