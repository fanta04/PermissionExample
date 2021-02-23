package com.fanta.service;

import com.alibaba.fastjson.JSONObject;

public interface LoginService {
    //登录表单提交
    JSONObject authLogin(JSONObject jsonObject);
    //从数据库中查询用户信息
    JSONObject getUser(String username,String passowrd);
    //从数据库中查询用户的权限信息
    JSONObject getInfo();
    //登出
    JSONObject logout();
}
