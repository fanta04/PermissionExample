package com.fanta.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fanta.dao.UserDao;
import com.fanta.service.UserService;
import com.fanta.util.CommonUtil;
import com.fanta.util.constants.ErrorEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.relation.RoleInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 用户的增删改查
     * */
    @Override
    public JSONObject listUser(JSONObject jsonObject) {
        CommonUtil.fillPageParam(jsonObject);
        int count = userDao.countUser();
        List<JSONObject> list = userDao.listUser(jsonObject);
        return CommonUtil.successPage(jsonObject,list,count);
    }


    @Override
    public JSONObject addUser(JSONObject jsonObject) {
        int count = userDao.queryExistUsername(jsonObject);
        if(count>0){
            return CommonUtil.errorJson(ErrorEnum.E_10009);
        }
        userDao.addUser(jsonObject);
        return CommonUtil.successJson(new JSONObject());
    }

    @Override
    public JSONObject updateUser(JSONObject jsonObject) {
        userDao.updateUser(jsonObject);
        return CommonUtil.successJson(new JSONObject());
    }

    @Override
    public JSONObject deleteUser(JSONObject jsonObject) {
        userDao.deleteUser(jsonObject);
        return CommonUtil.successJson(new JSONObject());
    }


    /**
     * 角色的增删改查
     * */
    @Override
    public JSONObject listRole() {
        List<JSONObject> list = userDao.listRole();
        return CommonUtil.successPage(list);
    }

    @Override
    public JSONObject addRole(JSONObject jsonObject) {
        userDao.addRole(jsonObject);
        userDao.addRolePermission(jsonObject.getString("roleId"),(List<Integer>)jsonObject.get("permissions"));
        return CommonUtil.successJson(new JSONObject());
    }


    /**
     * 修改角色：
     * 1.修改角色名
     * 2.添加新权限
     * 3.移除旧权限
     * */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public JSONObject updateRole(JSONObject jsonObject) {
        String roleId = jsonObject.getString("roleId");
        List<Integer> newPerms = (List<Integer>) jsonObject.get("permissions");
        JSONObject RoleInfo = userDao.getRoleAllInfo(jsonObject);
        List<Integer> oldPerms = (List<Integer>) RoleInfo.get("permissions");
        updateRoleName(jsonObject,RoleInfo);
        addRolePermission(roleId,newPerms,oldPerms);
        removeOldPermission(roleId,newPerms,oldPerms);
        return CommonUtil.successJson(new JSONObject());
    }

    //修改角色名
    public void updateRoleName(JSONObject jsonObject,JSONObject roleInfo) {
        String oldName = roleInfo.getString("roleName");
        if(!oldName.equals(jsonObject.getString("RoleName"))){
            userDao.updateRoleName(jsonObject);
        }
    }

    //添加新权限
    public void addRolePermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> newP = new ArrayList<>();
        for (Integer newPerm : newPerms) {
            if(!oldPerms.contains(newPerm)){
                newP.add(newPerm);
            }
        }
        if(!newP.isEmpty()){
            userDao.addRolePermission(roleId,newP);
        }
    }

    //移除旧权限
    public void removeOldPermission(String roleId, Collection<Integer> newPerms, Collection<Integer> oldPerms) {
        List<Integer> oldP = new ArrayList<>();
        for (Integer oldPerm : oldPerms) {
            if(!newPerms.contains(oldPerm)){
                oldP.add(oldPerm);
            }
        }
        if(!oldP.isEmpty()){
            userDao.removeOldPermission(roleId,oldP);
        }
    }

    //删除角色
    @Override
    public JSONObject removeRole(JSONObject jsonObject){
        userDao.removeRole(jsonObject);
        userDao.removeRoleAllPermission(jsonObject);
        return CommonUtil.successJson(new JSONObject());
    }
}
