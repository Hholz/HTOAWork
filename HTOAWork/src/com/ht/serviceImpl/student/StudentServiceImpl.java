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
	ShiroUserRoleMapper surMapper;//�û���ɫ��
	@Autowired
	ShiroUserInfoMapper suiMapper;//�û���Ϣ��
	@Override
	public int deleteById(Integer id) {
		return studentMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int addStudent(Student student) {
		int count = studentMapper.insertSelective(student);
		if(count>0){
			//���û���������û� stuno pwd**************************
			//����û���
			ShiroUser user = new ShiroUser();
			user.setName(student.getStuno());//�ñ��������
			if(null!=student.getPhone()){
				user.setPhone(student.getPhone());
			}
			user.setPwd(Base64.encodeToString("123456".getBytes()));
			user.setUserType(2);//2��ѧ��
			int count2 =userMapper.insertSelective(user);
			//����û���Ϣ��
			ShiroUserInfo userInfo = new ShiroUserInfo();
			userInfo.setUserId(user.getId());
			userInfo.setStuId(student.getId());
			int count3 =suiMapper.insertSelective(userInfo);
			//���û����Ĭ�Ͻ�ɫ
			ShiroUserRole sur = new ShiroUserRole();
			sur.setUserId(user.getId());
			sur.setRoleId(2);//ѧ��
			int count4 = surMapper.insertSelective(sur);
			if(count2>0&&count3>0&&count4>0){
				return 1;//�����ɹ�
			}else{
				logger.error("����ѧ���û��˺�ʧ��");//�û���Ϣ�����Ϣ����ʧ��
				count = 0;
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return count;
			}
		}else{
			return -1;//ѧ��������ʧ��
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
	@SystemServiceLog(description = "Service==��ѯ�û�list") 
	public List<Student> selectStudentAll() {
		return studentMapper.selectStudentAll();
	}

	@Override
	@SystemServiceLog(description = "Service==��ѯ�û�list") 
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
		//���û�÷���ֵ(�༶��һ��ѧ�������ʱ)֮�䷵��01��
		String stuno = studentMapper.findBigCode(classno);
		int num = 0;
		if(null==stuno){
			logger.info("�����༶"+classno+"�ĵ�һ��ѧ��������01");
		}else{
			try {
				String no = stuno.substring(stuno.length()-2, stuno.length());
				num = Integer.parseInt(no);
				logger.info("�༶"+classno+"����ѧ��������"+no);
			} catch (Exception e) {
				logger.error("�ַ�����ȡʧ�ܣ�ѧ�����="+stuno);
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
