package com.ht.serviceImpl.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.codec.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.ht.annotation.SystemControllerLog;
import com.ht.annotation.SystemServiceLog;
import com.ht.controller.student.StudentClassController;
import com.ht.mapper.login.ShiroUserInfoMapper;
import com.ht.mapper.login.ShiroUserMapper;
import com.ht.mapper.login.ShiroUserRoleMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.login.ShiroUser;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.login.ShiroUserRole;
import com.ht.popj.student.Student;
import com.ht.service.student.StudentService;

import common.Logger;

public class StudentServiceImpl implements StudentService{
	Logger logger = Logger.getLogger(StudentServiceImpl.class);

	@Autowired
	StudentMapper studentMapper;
	@Autowired
	ShiroUserMapper userMapper;
	@Autowired
	ShiroUserRoleMapper surMapper;//用户角色表
	@Autowired
	ShiroUserInfoMapper suiMapper;//用户信息表
	@Override
	public int deleteById(Integer id) {
		return studentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addStudent(Student student) {
		int count = studentMapper.insertSelective(student);
		if(count>0){
			//在用户表里添加用户 stuno pwd**************************
			//添加用户表
			ShiroUser user = new ShiroUser();
			user.setName(student.getStuno());//用编号做名字
			if(null!=student.getPhone()){
				user.setPhone(student.getPhone());
			}
			user.setPwd(Base64.encodeToString("123456".getBytes()));
			user.setUserType(2);//2是学生
			int count2 =userMapper.insertSelective(user);
			//添加用户信息表
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setStuId(student.getId());
			int count3 =suiMapper.insertSelective(userInfo);
			//给用户添加默认角色
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(2);//学生
			int count4 = surMapper.insertSelective(sur);
			if(count2>0&&count3>0&&count4>0){
				return 1;//新增成功
			}else{
				logger.error("新增学生用户账号失败");//用户信息相关信息新增失败
				count = 0;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return count;
			}
		}else{
			return -1;//学生表新增失败
		}
		
	}

	@Override
	public Student selectById(Integer id) {
		return studentMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateStuById(Student student) {
		return studentMapper.updateByPrimaryKeySelective(student);
	}
	@Override
	@SystemServiceLog(description = "Service==查询用户list") 
	public List<Student> selectStudentAll() {
		return studentMapper.selectStudentAll();
	}

	@Override
	@SystemServiceLog(description = "Service==查询用户list") 
	public List<Student> selectByDynamic(Student student) {
		return studentMapper.selectByDynamic(student);
	}
	@Override
	public int upStaById(Integer id) {
		return studentMapper.upStaById(id);
	}

	@Override
	public int upStuClsByIds(Map map) {
		return studentMapper.upStuClsByIds(map);
	}

	@Override
	public int upStuHuorByIds(Map map) {
		return studentMapper.upStuHuorByIds(map);
	}

	@Override
	public List<Student> selectByPJnoCls(Student student) {
		return studentMapper.selectByPJnoCls(student);
	}

	@Override
	public List<Student> selectByPJhasCls(Student record) {
		return studentMapper.selectByPJhasCls(record);
	}

	@Override
	public int countstudentseId(Integer id) {
		return studentMapper.countstudentseId(id);
	}

	public int findBigCode(String classno) {
		//如果没用返回值(班级第一个学生的情况时)之间返回01，
		String stuno = studentMapper.findBigCode(classno);
		int num = 0;
		if(null==stuno){
			logger.info("新增班级"+classno+"的第一个学生，返回01");
		}else{
			try {
				String no = stuno.substring(stuno.length()-2, stuno.length());
				num = Integer.parseInt(no);
				logger.info("班级"+classno+"新增学生，返回"+no);
			} catch (Exception e) {
				logger.error("字符串截取失败，学生编号="+stuno);
			}
		}
		return num;
	}

	@Override
	public List<Student> selectByClsIds(Map map) {
		return studentMapper.selectByClsIds(map);
	}

	@Override
	public Student findStuByNo(String stuno) {
		return studentMapper.findStuByNo(stuno);
	}

	@Override
	public List<Student> selectByClsIdsRandom(Map map) {
		// TODO Auto-generated method stub
		return studentMapper.selectByClsIdsRandom(map);
	}

	public List<Student> selectByclsId(Integer clsId) {
		return studentMapper.selectByclsId(clsId);
	}

	@Override
	public int updateStuStatusById(String id) {
		// TODO Auto-generated method stub
		return studentMapper.updateStuStatusById(id);
	}
}
