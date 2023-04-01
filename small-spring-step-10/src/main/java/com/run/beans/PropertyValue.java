package com.run.beans;

/**
 * @desc: 属性填充：属性值类
 * @author: AruNi_Lu
 * @date: 2023/3/13
 */
public class PropertyValue {

    /**
     * 属性名
     */
    private final String name;

    /**
     * 属性值
     */
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }


}
