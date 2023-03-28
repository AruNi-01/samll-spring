package com.run.context;

import com.run.beans.BeansException;
import com.run.beans.factory.Aware;

/**
 * @desc: ApplicationContext 感知接口，实现此接口能感知到所属的 ApplicationContext
 * @author: AruNi_Lu
 * @date: 2023/3/26
 */
public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
