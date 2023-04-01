package com.run.beans.factory;

/**
 * @desc: BeanClassLoader 感知接口，实现此接口能感知到所属的 BeanClassLoader
 * @author: AruNi_Lu
 * @date: 2023/3/26
 */
public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);

}
