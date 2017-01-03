package com.ht.mapper.login;

import java.util.List;

import com.ht.popj.login.ShiroRole;

public interface ShiroRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ShiroRole record);

    ShiroRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShiroRole record);

    List<ShiroRole> selectAll();
    
    List<ShiroRole> selectListByUserId(Integer userId);
}