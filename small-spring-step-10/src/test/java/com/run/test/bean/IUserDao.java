package com.run.test.bean;

/**
 * @desc: 只定义一个 UserDao 接口，为了通过 FactoryBean 做一个自定义对象的代理操作
 * @author: AruNi_Lu
 * @date: 2023/3/28
 */
public interface IUserDao {

    String queryUserName(String uId);

}
