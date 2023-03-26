package com.run.beans.factory;

/**
 * @desc: 销毁方法的接口
 * @author: AruNi_Lu
 * @date: 2023/3/23
 */
public interface DisposableBean {

    void destroy() throws Exception;

}
