package com.ht.mapper.login;

import com.ht.popj.login.ShiroUserInfo;

public interface ShiroUserInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShiroUserInfo record);

    int insertSelective(ShiroUserInfo record);

    ShiroUserInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShiroUserInfo record);

    int updateByPrimaryKey(ShiroUserInfo record);
    
    int delByUpdate(Integer id);
    
    ShiroUserInfo selectInfoByUserId(Integer id);
}