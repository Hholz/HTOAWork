package com.ht.service.login;

import java.util.List;

import com.ht.popj.login.ShiroUser;

public interface ShiroUserService {

	int deleteById(Integer id);

    int insertByPJ(ShiroUser user);

    ShiroUser selectById(Integer id);

    int updateByPJ(ShiroUser user);
    
    int delByUpdate(Integer id);
    
    List<ShiroUser> selectAll();
    
    ShiroUser selectByLogin(String str,String pwd);
    
    ShiroUser selectByName(ShiroUser user);

	int upNameByName(String oldStuno, String newStuno);
}
