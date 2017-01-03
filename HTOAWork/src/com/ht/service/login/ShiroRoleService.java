package com.ht.service.login;

import java.util.List;
import java.util.Map;

import com.ht.popj.login.ShiroRole;
import com.ht.popj.login.ShiroRoleAuth;

public interface ShiroRoleService {

	int deleteById(Integer id);

    int insertByPJ(ShiroRole shiroRole);

    ShiroRole selectById(Integer id);

    int updateByPJ(ShiroRole shiroRole);
    
    List<ShiroRole> selectAll();
    //通过用户id来查他有的权限
    List<ShiroRole> selectListByUserId(Integer userId);
   //删除用户权限
  	int insertRoleAuth(ShiroRoleAuth roleAuth);
    //删除用户权限
	int deleteBy2Id(Integer roleId, Integer authId);
}
