package com.ht.mapper.login;

import java.util.Map;

import com.ht.popj.login.ShiroUserRole;

public interface ShiroUserRoleMapper {
    int insert(ShiroUserRole record);

    int insertSelective(ShiroUserRole record);
    //Integer userId, Integer roleId
    int delBy2Id(Map map);
}