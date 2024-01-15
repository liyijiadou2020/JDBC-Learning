package com.atguigu.objects;

/**
 * @author: liyijia
 * @version: 0.0
 * @create time: 2024-01-2024/1/15
 * @description: 数据库用户类
 */
public class User {

    private String user;
    private String password;

    public User() {
    }

    public User(String user, String password) {
        super();
        this.user = user;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User [user=" + user + ", password=" + password + "]";
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}