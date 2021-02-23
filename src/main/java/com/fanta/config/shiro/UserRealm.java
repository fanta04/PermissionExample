package com.fanta.config.shiro;

import com.alibaba.fastjson.JSONObject;
import com.fanta.service.LoginService;
import com.fanta.util.constants.Constants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    @Override
    /**
     * loginService(getInfo方法：将用户的基本信息和权限信息放入session)<---
     * permissionService(getUserPermission)<---permissionMapper.xml拿到用户的权限列表
     *
     * 当碰到注解@RequiresPermission时调用这个授权方法
     * */
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        Session session = SecurityUtils.getSubject().getSession();
        //查询用户的权限
        JSONObject permission = (JSONObject) session.getAttribute(Constants.SESSION_USER_PERMISSION);
        //为当前用户设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.addStringPermissions((Collection<String>) permission.get("permissionList"));
        return authorizationInfo;
    }


    /**
     * subject.login(token)最终会调用到doGetAuthenticationInfo这个认证方法
     * */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取输入的用户名和密码
        String loginName = (String) authenticationToken.getPrincipal();
        String passowrd = new String((char[]) authenticationToken.getCredentials());
        //从数据库中查询用户名和密码
        JSONObject user = loginService.getUser(loginName, passowrd);
        if(user == null){
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                user.getString("username"),
                user.getString("password"),
                this.getName()
        );
        user.remove("password");

        //当认证通过之后将用户的基本信息放入session里
        SecurityUtils.getSubject().getSession().setAttribute(Constants.SESSION_USER_INFO,user);
        return simpleAuthenticationInfo;
    }
}
