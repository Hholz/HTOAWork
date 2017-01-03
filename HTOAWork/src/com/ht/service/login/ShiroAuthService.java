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
	//ͨ����ɫid������ӵ�е�Ȩ��
    List<ShiroAuth> selectByAuthId(Integer id);
}
