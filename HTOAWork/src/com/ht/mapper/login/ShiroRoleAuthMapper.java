package com.ht.mapper.login;

import java.util.List;
import java.util.Map;

import com.ht.popj.login.ShiroRoleAuth;

public interface ShiroRoleAuthMapper {
    int insert(ShiroRoleAuth record);

    int insertSelective(ShiroRoleAuth record);
    
    List<ShiroRoleAuth> selectAll();
    
    int deleteBy2Id(Map map);
}