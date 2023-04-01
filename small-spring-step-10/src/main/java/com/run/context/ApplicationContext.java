package com.run.context;

import com.run.beans.factory.ListableBeanFactory;

/**
 * @desc: 应用上下文：ApplicationContext 本身是 Central 接口，但目前还不需要添加一些获取 ID 和父类上下文，所以暂时没有接口方法的定义。
 * 此外，该接口继承于 ListableBeanFactory，也就继承了关于 BeanFactory 方法，比如一些 getBean 的方法。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface ApplicationContext extends ListableBeanFactory {
}
