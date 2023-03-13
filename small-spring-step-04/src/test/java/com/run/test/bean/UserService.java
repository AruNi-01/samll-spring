package com.run.test.bean;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class UserService {

    private String uId;

    // 依赖 UserDao
    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + userDao.queryUserName(uId));
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
}
