package com.fanta.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanta.dao.PermissionDao;
import com.fanta.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    //查询某用户的id，用户名，菜单列表，权限列表
    @Override
    public JSONObject getUserPermissions(String username) {
        JSONObject userPermissions = getUserPermissionFromDB(username);
        return userPermissions;
    }

    //查询数据库中的某用户id，用户名，菜单列表，权限列表
    //其实是判断是否为amdin用户，并赋予admin用户所有权限，因为数据库里没写admin用户的权限
    @Override
    public JSONObject getUserPermissionFromDB(String username) {
        JSONObject permissions = permissionDao.getUserPermissions(username);
        int adminRoleId = 1;
        String roleKey = "roleId";
        if(adminRoleId == permissions.getIntValue(roleKey)){
            //查询所有菜单，所有权限。赋给该用户
            Set<String> allMenu = permissionDao.getAllMenu();
            Set<String> allPermissions = permissionDao.getAllPermissions();
            permissions.put("menuList",allMenu);
            permissions.put("permissionList",allPermissions);
        }
        return permissions;
    }


}
