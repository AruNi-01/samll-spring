package com.run.context.support;

/**
 * @desc: ClassPathXmlApplicationContext，是具体对外给用户提供的应用上下文方法。
 * 在继承了 AbstractXmlApplicationContext 以及层层抽象类的功能分离实现后，此类
 * 的实现就简单多了，主要是对继承抽象类中方法的调用和提供了配置文件地址信息。
 * @author: AruNi_Lu
 * @date: 2023/3/19
 */
public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {

    private String[] configLocations;

    public ClassPathXmlApplicationContext() {
    }

    /**
     * 从 XML 中获取 Bean 信息，并执行 refresh() 刷新上下文
     * @param configLocations
     */
    public ClassPathXmlApplicationContext(String[] configLocations) {
        this.configLocations = configLocations;
        refresh();
    }

    public ClassPathXmlApplicationContext(String configLocations) {
        this(new String[]{configLocations});
    }

    @Override
    protected String[] getConfigLocations() {
        return configLocations;
    }
}
