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
    
    //ͨ�����ֺ�������ȡ
    List<ShiroUser> selectByNamePwd(ShiroUser record);
    //ͨ��id��������ȡ
    List<ShiroUser> selectByIdPwd(ShiroUser record);
    //ͨ���绰��������ȡ
    List<ShiroUser> selectByPhonePwd(ShiroUser record);
    
    List<ShiroUser> selectByName(ShiroUser user);

	int upNameByName(Map map);//ͨ���ϵ�����ȥ������
}