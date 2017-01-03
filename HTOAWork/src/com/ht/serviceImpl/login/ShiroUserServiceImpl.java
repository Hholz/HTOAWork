package com.ht.serviceImpl.login;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.login.ShiroUserMapper;
import com.ht.popj.login.ShiroUser;
import com.ht.service.login.ShiroUserService;

public class ShiroUserServiceImpl implements ShiroUserService{

	@Autowired
	ShiroUserMapper userMapper;
	@Override
	public int deleteById(Integer id) {
		return userMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(ShiroUser user) {
		return userMapper.insertSelective(user);
	}

	@Override
	public ShiroUser selectById(Integer id) {
		return userMapper.selectById(id);
	}

	@Override
	public int updateByPJ(ShiroUser user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public int delByUpdate(Integer id) {
		return userMapper.delByUpdate(id);
	}

	@Override
	public List<ShiroUser> selectAll() {
		return userMapper.selectAll();
	}

	/*
	 * 在这里提供id，密码/姓名，密码/电话，密码 来查询出用户信息
	 * @see com.ht.service.login.ShiroUserService#selectByLogin(java.lang.String, java.lang.String)
	 */
	@Override
	public ShiroUser selectByLogin(String str, String pwd) {
		//ShiroUser UserInfo = new ShiroUser();
		List<ShiroUser> list  = new ArrayList<ShiroUser>();
		List<ShiroUser> list2  = new ArrayList<ShiroUser>();
		List<ShiroUser> list3  = new ArrayList<ShiroUser>();
		ShiroUser user = new ShiroUser();
		try {
			user.setId(Integer.parseInt(str));
		} catch (Exception e) {
			
		}
		user.setPwd(pwd);
		ShiroUser user2 = new ShiroUser();
		user2.setName(str);
		user2.setPwd(pwd);
		ShiroUser user3 = new ShiroUser();
		user3.setPhone(str);
		user3.setPwd(pwd);
		list =userMapper.selectByIdPwd(user);
		list2 =userMapper.selectByNamePwd(user2);
		list3 =userMapper.selectByPhonePwd(user3);
		if(list.size()==1){
			return list.get(0);
		}else if(list2.size()==1){
			return list2.get(0);
		}else if(list3.size()==1){
			return list3.get(0);
		}else{
			return null;
		}
	}

	@Override
	public ShiroUser selectByName(ShiroUser user) {
		List<ShiroUser> list  = new ArrayList<ShiroUser>();
		list =userMapper.selectByName(user);
		if(list.size()>=1){
			return list.get(0);
		}else{
			return null;
		}
	}

	public int upNameByName(String oldStuno, String newStuno) {
		Map map = new HashMap();
		map.put("oldStuno", oldStuno);
		map.put("newStuno", newStuno);
		return userMapper.upNameByName(map);
	}

}
