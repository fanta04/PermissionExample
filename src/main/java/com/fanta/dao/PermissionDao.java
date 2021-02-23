package com.fanta.dao;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Mapper
@Repository
public interface PermissionDao {
    JSONObject getUserPermissions(String username);
    Set<String> getAllMenu();
    Set<String> getAllPermissions();
}
