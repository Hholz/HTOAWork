package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.mapper.login.ShiroUserInfoMapper;
import com.ht.mapper.login.ShiroUserMapper;
import com.ht.mapper.login.ShiroUserRoleMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.service.dailyWork.EmpService;

import common.Logger;

public class EmpServiceImpl  implements EmpService{
	Logger logger = Logger.getLogger(EmpServiceImpl.class);
	
	@Autowired
	EmpMapper empMapper;
	@Autowired
	ShiroUserMapper userMapper;
	@Autowired
	ShiroUserRoleMapper surMapper;//�û���ɫ��
	@Autowired
	ShiroUserInfoMapper suiMapper;//�û���Ϣ��

	@Override
	public int deleteByPrimaryKey(String id) {
		return empMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Emp emp) {
		empMapper.insert(emp);
		
		int count = 0;
		ShiroUser user = new ShiroUser();
		user.setName(emp.getEmpname());
		if(null!=emp.getPhone()){
			user.setPhone(emp.getPhone());
		}
		user.setPwd(Base64.encodeToString("123456".getBytes()));
		user.setUserType(1);//1��Ա��
		int count2 =userMapper.insertSelective(user);
		//����û���Ϣ��
		ShiroUserInfo userInfo = new ShiroUserInfo();
		userInfo.setUserId(user.getId());
		userInfo.setEmpId(emp.getId());
		int count3 =suiMapper.insertSelective(userInfo);
		//���û����Ĭ�Ͻ�ɫ
		ShiroUserRole sur = new ShiroUserRole();
		sur.setUserId(user.getId());
		sur.setRoleId(1);//Ա��
		int count4 = surMapper.insertSelective(sur);
		if(count2>0&&count3>0&&count4>0){
			count++;
		}else{
			logger.error("����Ա���û��˺�ʧ��");
			count = 0;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return count;
	}

	@Override
	public Emp selectByPrimaryKey(String id) {
		return empMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(Emp record) {
		return empMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<Emp> selectEmp(Emp emp) {
		return empMapper.selectEmp(emp);
	}

	@Override
	public Emp selectEmpById(String id) {
		return empMapper.selectEmpById(id);
	}

	@Override
	public List<Emp> selectByDepid(Integer depid) {
		return empMapper.selectByDepid(depid);
	}
	
}
