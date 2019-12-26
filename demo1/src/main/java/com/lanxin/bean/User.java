package com.lanxin.bean;

public class User {
    private Integer id;

    private String username;

    private String password;

    private Integer age;
    
    private String user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
    public String userId(String user_id) {
    	return this.user_id=user_id;
    }
    public String getUserId() {
        return user_id;
    }
}