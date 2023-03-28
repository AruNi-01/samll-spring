package com.run.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc:
 * @author: AruNi_Lu
 * @date: 2023/3/13
 */
public class UserDao {

    private static Map<String, String> map = new HashMap<>();


    public void initDataMethod() {
        System.out.println("userDao 执行 init-method");
        map.put("10001", "孙悟空");
        map.put("10002", "猪八戒");
        map.put("10003", "沙悟净");
    }

    public void destroyDataMethod() {
        System.out.println("userDao 执行 destroy-method");
        map.clear();
    }

    public String queryUserName(String uId) {
        return map.get(uId);
    }
}
