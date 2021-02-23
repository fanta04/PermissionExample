package com.fanta.service;

import com.alibaba.fastjson.JSONObject;

public interface PermissionService {
    JSONObject getUserPermissions(String username);

    JSONObject getUserPermissionFromDB(String username);
}
