package com.ht.mapper.login;

import java.util.List;

import com.ht.popj.login.ShiroAuth;

public interface ShiroAuthMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(ShiroAuth record);

    ShiroAuth selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShiroAuth record);

    List<ShiroAuth> selectAll();
    
    List<ShiroAuth> selectByTypeId(Integer id);
    //ͨ����ɫid������ӵ�е�Ȩ��
    List<ShiroAuth> selectByAuthId(Integer id);
}