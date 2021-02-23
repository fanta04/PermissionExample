package com.fanta.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanta.dao.LoginDao;
import com.fanta.service.LoginService;
import com.fanta.service.PermissionService;
import com.fanta.util.CommonUtil;
import com.fanta.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;
    @Autowired
    private PermissionService permissionService;

    @Override
    public JSONObject authLogin(JSONObject jsonObject) {
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        JSONObject info = new JSONObject();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
            info.put("result","success");
        }catch (AuthenticationException e){
            info.put("result","fail");
        }
        return CommonUtil.successJson(info);
    }

    @Override
    public JSONObject getUser(String username, String passowrd) {
        return loginDao.getUser(username,passowrd);
    }

    @Override
    public JSONObject getInfo() {
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String username = userInfo.getString("username");
        JSONObject info = new JSONObject();
        JSONObject userPermissions = permissionService.getUserPermissions(username);
        session.setAttribute(Constants.SESSION_USER_PERMISSION,userPermissions);
        info.put("userPermissions",userPermissions);
        return CommonUtil.successJson(info);
    }

    @Override
    public JSONObject logout() {
        try{
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
        }catch (Exception e){
            e.printStackTrace();
        }
        return CommonUtil.successJson(new JSONObject());
    }
}
