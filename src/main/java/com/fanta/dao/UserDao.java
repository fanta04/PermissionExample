package com.fanta.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserDao {

    //用户增删改查
    int countUser();
    List<JSONObject> listUser(JSONObject jsonObject);
    int queryExistUsername(JSONObject jsonObject);
    int addUser(JSONObject jsonObject);
    int updateUser(JSONObject jsonObject);
    int deleteUser(JSONObject jsonObject);

    //角色增删改查
    List<JSONObject> listRole();
    int addRole(JSONObject jsonObject);
    int addRolePermission(@Param("roleId")String roleId,@Param("permissions")List<Integer>permissions);
    JSONObject getRoleAllInfo(JSONObject jsonObject);
    int updateRoleName(JSONObject jsonObject);
    int removeOldPermission(@Param("roleId")String roleId,@Param("permissions")List<Integer>permissions);
    int removeRole(JSONObject jsonObject);
    int removeRoleAllPermission(JSONObject jsonObject);
}
