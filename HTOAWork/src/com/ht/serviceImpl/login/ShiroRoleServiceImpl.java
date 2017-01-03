package com.ht.serviceImpl.login;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.login.ShiroRoleAuthMapper;
import com.ht.mapper.login.ShiroRoleMapper;
import com.ht.popj.login.ShiroRole;
import com.ht.popj.login.ShiroRoleAuth;
import com.ht.service.login.ShiroRoleService;

public class ShiroRoleServiceImpl implements ShiroRoleService{

	@Autowired
	ShiroRoleMapper shiroRoleMapper;
	@Autowired
	ShiroRoleAuthMapper shiroRoleAuthMapper;
	@Override
	public int deleteById(Integer id) {
		return shiroRoleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(ShiroRole shiroRole) {
		return shiroRoleMapper.insertSelective(shiroRole);
	}

	@Override
	public ShiroRole selectById(Integer id) {
		return shiroRoleMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(ShiroRole shiroRole) {
		return shiroRoleMapper.updateByPrimaryKeySelective(shiroRole);
	}

	@Override
	public List<ShiroRole> selectAll() {
		return shiroRoleMapper.selectAll();
	}
	@Override
	public int deleteBy2Id(Integer roleId, Integer authId) {
		Map map = new HashMap();
		map.put("roleId", roleId);
		map.put("authId", authId);
		return shiroRoleAuthMapper.deleteBy2Id(map);
	}

	@Override
	public int insertRoleAuth(ShiroRoleAuth roleAuth) {
		return shiroRoleAuthMapper.insertSelective(roleAuth);
	}

	@Override
	public List<ShiroRole> selectListByUserId(Integer userId) {
		return shiroRoleMapper.selectListByUserId(userId);
	}

}
