package com.run.beans.factory;

/**
 * @desc: 初始化方法的接口
 * @author: AruNi_Lu
 * @date: 2023/3/23
 */
public interface InitializingBean {

    /**
     * Bean 进行属性填充后再调用
     * @throws Exception
     */
    void afterPropertiesSet() throws Exception;

}
