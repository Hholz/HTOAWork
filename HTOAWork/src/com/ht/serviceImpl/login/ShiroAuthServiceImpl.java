package com.ht.serviceImpl.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.login.ShiroAuthMapper;
import com.ht.mapper.login.ShiroAuthTypeMapper;
import com.ht.popj.login.ShiroAuth;
import com.ht.popj.login.ShiroAuthType;
import com.ht.service.login.ShiroAuthService;

public class ShiroAuthServiceImpl implements ShiroAuthService{

	@Autowired
	ShiroAuthMapper shiroAuthMapper;
	@Autowired
	ShiroAuthTypeMapper shiroAuthTypeMapper;
	@Override
	public int deleteById(Integer id) {
		return shiroAuthMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(ShiroAuth shiroAuth) {
		return shiroAuthMapper.insertSelective(shiroAuth);
	}

	@Override
	public ShiroAuth selectById(Integer id) {
		return shiroAuthMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(ShiroAuth shiroAuth) {
		return shiroAuthMapper.updateByPrimaryKeySelective(shiroAuth);
	}

	@Override
	public List<ShiroAuth> selectAll() {
		return shiroAuthMapper.selectAll();
	}

	@Override
	public List<ShiroAuthType> selectAllType() {
		return shiroAuthTypeMapper.selectAll();
	}

	/*
	 *通过类别id来查该类别的所有权限
	 * @see com.ht.service.login.ShiroAuthService#selectByTypeId(java.lang.Integer)
	 */
	@Override
	public List<ShiroAuth> selectByTypeId(Integer id) {
		return shiroAuthMapper.selectByTypeId(id);
	}

	@Override
	public List<ShiroAuth> selectByAuthId(Integer id) {
		return shiroAuthMapper.selectByAuthId(id);
	}

}
