package com.ht.service.login;

import com.ht.popj.login.ShiroUserInfo;


public interface ShiroUserInfoService {

	int deleteById(Integer id);

    int insertByPJ(ShiroUserInfo user);

    ShiroUserInfo selectById(Integer id);

    int updateByPJ(ShiroUserInfo user);
    
    int delByUpdate(Integer id);
    
    ShiroUserInfo selectByUserId(Integer id);
}
