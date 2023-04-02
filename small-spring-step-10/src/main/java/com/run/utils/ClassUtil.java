package com.run.utils;

/**
 * @desc: 类相关的工具类
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public class ClassUtil {

    /**
     * 获取默认的类加载器
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex) {
            // Cannot access thread context ClassLoader - falling back to system class loader...
        }
        if (cl == null) {
            // No thread context class loader -> use class loader of this class.
            cl = ClassUtil.class.getClassLoader();
        }
        return cl;
    }

    /**
     * 检查指定的类是否为 cglib 生成的类
     */
    public static boolean isCglibProxyClass(Class<?> clazz) {
        return (clazz != null && isCglibProxyClassName(clazz.getName()));
    }
    public static boolean isCglibProxyClassName(String className) {
        return (className != null && className.contains("$$"));
    }


}
