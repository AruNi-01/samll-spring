package com.run.context;

import com.run.beans.BeansException;

/**
 * @desc: ConfigurableApplicationContext 继承自 ApplicationContext，并提供了 refresh 这个核心方法。
 * 接下来也是需要在上下文的实现中完成刷新容器的操作过程。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     * @throws BeansException
     */
    void refresh() throws BeansException;

}
