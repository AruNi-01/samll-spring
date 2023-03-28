package com.run.beans.factory;

import com.run.beans.BeansException;

/**
 * @desc: BeanFactory 感知接口，实现此接口能感知到所属的 BeanFactory
 * @author: AruNi_Lu
 * @date: 2023/3/26
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
