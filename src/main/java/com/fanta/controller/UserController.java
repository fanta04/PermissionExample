package com.fanta.controller;


import com.alibaba.fastjson.JSONObject;
import com.fanta.service.UserService;
import com.fanta.util.CommonUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户的增删改查
     * */

    @RequiresPermissions("user:list")
    @RequestMapping("listUser")
    public JSONObject listUser(HttpServletRequest request){
        JSONObject jsonObject = CommonUtil.request2Json(request);
        return userService.listUser(jsonObject);
    }

    @RequiresPermissions("user:add")
    @RequestMapping("addUser")
    public JSONObject addUser(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"username,nickname,password,roleId");
        return userService.addUser(jsonObject);
    }

    @RequiresPermissions("user:update")
    @RequestMapping("updateUser")
    public JSONObject updateUser(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"userId,nickname,roleId,deleteStatus");
        return userService.updateUser(jsonObject);
    }

    @RequiresPermissions("user:delete")
    @RequestMapping("deleteUser")
    public JSONObject deleteUser(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"userId");
        return userService.deleteUser(jsonObject);
    }

    /**
     * 角色的增删改查
     * */

    @RequiresPermissions("role:list")
    @RequestMapping("listRole")
    public JSONObject listRole(){
        return userService.listRole();
    }

    @RequiresPermissions("role:add")
    @RequestMapping("addRole")
    public JSONObject addRole(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"roleName,permissions");
        return userService.addRole(jsonObject);
    }

    @RequiresPermissions("role:update")
    @RequestMapping("updateRole")
    public JSONObject updateRole(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"roleId,roleName,permissions");
        return userService.updateRole(jsonObject);
    }

    @RequiresPermissions("role:delete")
    @RequestMapping("deleteRole")
    public JSONObject deleteRole(@RequestBody JSONObject jsonObject){
        CommonUtil.hasAllRequiredParam(jsonObject,"roleId");
        return userService.removeRole(jsonObject);
    }
}
