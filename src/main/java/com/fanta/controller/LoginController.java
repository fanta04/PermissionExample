package com.fanta.controller;


import com.alibaba.fastjson.JSONObject;
import com.fanta.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("auth")
    public JSONObject authLogin(@RequestBody JSONObject jsonObject){
        return loginService.authLogin(jsonObject);
    }

    @RequestMapping("getInfo")
    public JSONObject getInfo(){
        return loginService.getInfo();
    }

    @RequestMapping("logout")
    public JSONObject logout(){return loginService.logout();}
}
