package com.fanta.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;


public interface UserService {

    JSONObject listUser(JSONObject jsonObject);
    JSONObject addUser(JSONObject jsonObject);
    JSONObject updateUser(JSONObject jsonObject);
    JSONObject deleteUser(JSONObject jsonObject);

    JSONObject listRole();
    JSONObject addRole(JSONObject jsonObject);
    JSONObject updateRole(JSONObject jsonObject);
//    void updateRoleName(JSONObject jsonObject,JSONObject roleInfo);
//    void addRolePermission(String roleId, Collection<Integer>newPerm,Collection<Integer>oldPerm);
//    void removeOldPermission(String roleId, Collection<Integer>newPerm,Collection<Integer>oldPerm);
    JSONObject removeRole(JSONObject jsonObject);

}
