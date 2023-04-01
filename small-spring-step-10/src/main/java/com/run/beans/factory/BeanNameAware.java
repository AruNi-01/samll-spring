package com.run.beans.factory;

/**
 * @desc: BeanName 感知接口，实现此接口能感知到所属的 BeanName
 * @author: AruNi_Lu
 * @date: 2023/3/26
 */
public interface BeanNameAware extends Aware {

    void setBeanName(String name);

}
