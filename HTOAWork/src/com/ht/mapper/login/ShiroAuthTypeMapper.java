package com.ht.mapper.login;

import java.util.List;

import com.ht.popj.login.ShiroAuthType;

public interface ShiroAuthTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ShiroAuthType record);

    ShiroAuthType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShiroAuthType record);

    List<ShiroAuthType> selectAll();
}