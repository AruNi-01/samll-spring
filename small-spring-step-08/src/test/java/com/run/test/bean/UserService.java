package com.run.test.bean;

import com.run.beans.factory.DisposableBean;
import com.run.beans.factory.InitializingBean;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class UserService implements InitializingBean, DisposableBean {

    private String uId;

    // 新增加了 company、location，两个属性信息，便于测试 BeanPostProcessor、
    // BeanFactoryPostProcessor 两个接口对 Bean 属性信息扩展的作用。
    private String company;
    private String location;

    // 依赖 UserDao
    private UserDao userDao;

    @Override
    public void destroy() throws Exception {
        System.out.println("执行 UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行 UserService.afterPropertiesSet");
    }

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "，公司：" + company + "，地点：" + location;
    }

    public String getuId() {
        return uId;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
