package com.ht.serviceImpl.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.login.ShiroUserInfoMapper;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.service.login.ShiroUserInfoService;

public class ShiroUserInfoServiceImpl implements ShiroUserInfoService{

	@Autowired
	ShiroUserInfoMapper suiMapper;
	@Override
	public int deleteById(Integer id) {
		return suiMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(ShiroUserInfo user) {
		return suiMapper.insert(user);
	}

	@Override
	public ShiroUserInfo selectById(Integer id) {
		return suiMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(ShiroUserInfo user) {
		return suiMapper.updateByPrimaryKey(user);
	}

	@Override
	public int delByUpdate(Integer id) {
		return suiMapper.delByUpdate(id);
	}

	@Override
	public ShiroUserInfo selectByUserId(Integer id) {
		return suiMapper.selectInfoByUserId(id);
	}

}
