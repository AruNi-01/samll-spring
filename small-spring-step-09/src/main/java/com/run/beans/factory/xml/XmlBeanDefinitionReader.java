package com.run.beans.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.run.beans.BeansException;
import com.run.beans.PropertyValue;
import com.run.beans.PropertyValues;
import com.run.beans.factory.config.BeanDefinition;
import com.run.beans.factory.config.BeanReference;
import com.run.beans.factory.support.AbstractBeanDefinitionReader;
import com.run.beans.factory.support.BeanDefinitionReader;
import com.run.beans.factory.support.BeanDefinitionRegistry;
import com.run.core.io.Resource;
import com.run.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;

/**
 * @desc: 对 XML 文件的解析，把我们本来在代码中的操作放到了通过解析 XML 自动注册的方式
 * @author: AruNi_Lu
 * @date: 2023/3/14
 */
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
        super(registry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        // 先获取该资源的输入流，再调用 doLoadBeanDefinitions() 方法做具体的解析、注册 Bean
        try {
            try (InputStream inputStream = resource.getInputStream()) {
                doLoadBeanDefinitions(inputStream);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    @Override
    public void loadBeanDefinitions(Resource... resources) throws BeansException {
        for (Resource resource : resources) {
            loadBeanDefinitions(resource);
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }

    @Override
    public void loadBeanDefinitions(String... locations) throws BeansException {
        for (String location : locations) {
            loadBeanDefinitions(location);
        }
    }

    // 具体的解析 XML、注册 Bean（可以对照一份 spring.xml 文件理解，其实就是解析出文件中的 bean，名字，值等信息)
    protected void doLoadBeanDefinitions(InputStream inputStream) throws ClassNotFoundException {
        Document doc = XmlUtil.readXML(inputStream);
        Element root = doc.getDocumentElement();
        // 先获取最顶层的 <beans> </beans> 标签
        NodeList childNodes = root.getChildNodes();

        // 遍历 <beans> 标签，获取里面的 <bean /> 标签
        for (int i = 0; i < childNodes.getLength(); i++) {
            // 判断元素
            if (!(childNodes.item(i) instanceof Element)) continue;
            // 判断对象
            if (!"bean".equals(childNodes.item(i).getNodeName())) continue;

            // 解析标签：<bean />
            Element bean = (Element) childNodes.item(i);
            String id = bean.getAttribute("id");
            String name = bean.getAttribute("name");
            String className = bean.getAttribute("class");
            String initMethodName = bean.getAttribute("init-method");
            String destroyMethodName = bean.getAttribute("destroy-method");
            // 新增对作用域的读取
            String beanScope = bean.getAttribute("scope");

            // 获取 Class，方便获取类中的名称
            Class<?> clazz = Class.forName(className);
            // 优先级：id > name
            String beanName = StrUtil.isNotEmpty(id) ? id : name;
            if (StrUtil.isEmpty(beanName)) {
                beanName = StrUtil.lowerFirst(clazz.getSimpleName());
            }

            // 创建 BeanDefinition
            BeanDefinition beanDefinition = new BeanDefinition(clazz);

            // 额外将 init-method、destroy-method 设置到 beanDefinition 中
            beanDefinition.setInitMethodName(initMethodName);
            beanDefinition.setDestroyMethodName(destroyMethodName);

            // 设置 Bean 的 scope
            if (StrUtil.isNotEmpty(beanScope)) {
                beanDefinition.setScope(beanScope);
            }

            // 读取属性并填充：遍历 <bean /> 标签，获取里面的 <property /> 标签
            for (int j = 0; j < bean.getChildNodes().getLength(); j++) {
                if (!(bean.getChildNodes().item(j) instanceof Element)) continue;
                if (!"property".equals(bean.getChildNodes().item(j).getNodeName())) continue;
                // 解析标签：<property />
                Element property = (Element) bean.getChildNodes().item(j);
                String attrName = property.getAttribute("name");
                String attrValue = property.getAttribute("value");
                String attrRef = property.getAttribute("ref");
                // 获取属性值，分为对象类型和基本类型
                Object value = StrUtil.isNotEmpty(attrRef) ? new BeanReference(attrRef) : attrValue;
                // 创建属性信息，添加进属性集合 PropertyValues 中
                PropertyValue propertyValue = new PropertyValue(attrName, value);
                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);
            }
            // 不允许重复注册 Bean
            if (getRegistry().containsBeanDefinition(beanName)) {
                throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
            }

            // 注册 Bean，beanDefinition 中有 PropertyValues，在执行 beanFactory.getBean() 时，
            // 就会在创建 Bean 时（实例化 Bean 之后）进行依赖属性的填充。
            getRegistry().registerBeanDefinition(beanName, beanDefinition);
        }
    }
}
