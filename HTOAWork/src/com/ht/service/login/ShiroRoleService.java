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
    //ͨ���û�id�������е�Ȩ��
    List<ShiroRole> selectListByUserId(Integer userId);
   //ɾ���û�Ȩ��
  	int insertRoleAuth(ShiroRoleAuth roleAuth);
    //ɾ���û�Ȩ��
	int deleteBy2Id(Integer roleId, Integer authId);
}
