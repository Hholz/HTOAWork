package com.ht.serviceImpl.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.login.ShiroUserRoleMapper;
import com.ht.popj.login.ShiroUserRole;
import com.ht.service.login.ShiroUserRoleService;

public class ShiroUserRoleServiceImpl implements ShiroUserRoleService{

	@Autowired
	ShiroUserRoleMapper surMapper;

	@Override
	public int insertByPJ(ShiroUserRole sur) {
		return surMapper.insertSelective(sur);
	}

	@Override
	public int delBy2Id(Integer userId, Integer roleId) {
		Map map = new HashMap();
		map.put("userId", userId);
		map.put("roleId", roleId);
		return surMapper.delBy2Id(map);
	}
	
}
