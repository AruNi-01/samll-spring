package com.run.test.bean;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/8
 */
public class UserService {

    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public UserService() {
    }

    public void queryUserInfo() {
        System.out.println("查询用户信息: " + name);
    }

    @Override
    public String toString() {
        return "UserService{" +
                "name='" + name + '\'' +
                '}';
    }
}
