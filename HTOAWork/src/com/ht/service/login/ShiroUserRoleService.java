package com.ht.service.login;

import com.ht.popj.login.ShiroUserRole;

public interface ShiroUserRoleService {

    int insertByPJ(ShiroUserRole user);

	int delBy2Id(Integer userId, Integer roleId);
    
}
