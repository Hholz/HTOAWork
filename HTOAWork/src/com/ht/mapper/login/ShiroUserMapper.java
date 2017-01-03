package com.ht.mapper.login;

import java.util.List;
import java.util.Map;

import com.ht.popj.login.ShiroUser;
public interface ShiroUserMapper {
	
	int selectShiroByUserInfo(String empid);
	
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ShiroUser record);

    ShiroUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ShiroUser record);

    int delByUpdate(Integer id);
    
    List<ShiroUser> selectAll();
    
    ShiroUser selectById(Integer id);
    
    //通过名字和密码来取
    List<ShiroUser> selectByNamePwd(ShiroUser record);
    //通过id和密码来取
    List<ShiroUser> selectByIdPwd(ShiroUser record);
    //通过电话和密码来取
    List<ShiroUser> selectByPhonePwd(ShiroUser record);
    
    List<ShiroUser> selectByName(ShiroUser user);

	int upNameByName(Map map);//通过老的名字去改名字
}