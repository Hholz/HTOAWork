package com.ht.service.login;

import java.util.List;

import com.ht.popj.login.ShiroAuth;
import com.ht.popj.login.ShiroAuthType;

public interface ShiroAuthService {

	int deleteById(Integer id);

    int insertByPJ(ShiroAuth shiroAuth);

    ShiroAuth selectById(Integer id);

    int updateByPJ(ShiroAuth shiroAuth);
    
    List<ShiroAuth> selectAll();
    
    List<ShiroAuthType> selectAllType();

	List<ShiroAuth> selectByTypeId(Integer id);
	//通过角色id来查他拥有的权限
    List<ShiroAuth> selectByAuthId(Integer id);
}
