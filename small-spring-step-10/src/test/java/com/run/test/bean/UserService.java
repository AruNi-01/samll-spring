package com.run.test.bean;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class UserService {

    private String uId;
    private String company;
    private String location;
    // 改为 IUserDao，后面会给这个属性注入代理对象。
    private IUserDao userDao;

    public String queryUserInfo() {
        return userDao.queryUserName(uId) + "," + company + "," + location;
    }

    public String getuId() {
        return uId;
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public String getLocation() {
        return location;
    }
}
