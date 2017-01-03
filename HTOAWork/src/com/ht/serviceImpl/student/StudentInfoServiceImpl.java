package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentFallMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.student.StudentInfoService;

import common.Logger;

public class StudentInfoServiceImpl implements StudentInfoService{
	Logger logger = Logger.getLogger(StudentInfoServiceImpl.class);

	@Autowired
	private StudentFallMapper studentFallMapper;
	@Autowired
	private StudentClassMapper studentClassMapper;
	@Autowired
	StudentMapper studentMapper;
	
	@Override
	public List<StudentFall> selectStudentFall() throws Exception {
		
		return studentFallMapper.selectStudentFall();
	}
	@Override
	public void insertSelective(StudentFall record) throws Exception {
		// TODO Auto-generated method stub
		studentFallMapper.insertSelective(record);
	}
	@Override
	public int updateByPrimaryKeySelective(StudentFall record) {
		// TODO Auto-generated method stub
		return studentFallMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<StudentFall> selectByDynamic(StudentFall record) {
		// TODO Auto-generated method stub
		return studentFallMapper.selectByDynamic(record);
	}
	@Override
	public int insertSelective(StudentClass record) {
		// TODO Auto-generated method stub
		return studentClassMapper.insertSelective(record);
	}
	@Override
	public List<StudentClass> selectStudentclass(StudentClass record) {
		// TODO Auto-generated method stub
		return studentClassMapper.selectStudentclass(record);
	}
	@Override
	public int updateByPrimaryKeySelective(StudentClass record) {
		// TODO Auto-generated method stub
		return studentClassMapper.updateByPrimaryKeySelective(record);
	}
	@Override
	public List<StudentFall> selectStufallclass(int id) {
		// TODO Auto-generated method stub
		return studentFallMapper.selectStufallclass(id);
	}
	@Override
	public List<StudentClass> selectStudentclass2(int id) {
		// TODO Auto-generated method stub
		return studentClassMapper.selectStudentclass2(id);
	}
	@Override
	public List<StudentFall> selectStufallclasstwo() {
		// TODO Auto-generated method stub
		return studentFallMapper.selectStufallclasstwo();
	}
	@Override
	public List<StudentClass> selectallstduentclass() {
		// TODO Auto-generated method stub
		return studentClassMapper.selectallstduentclass();
	}
	@Override
	public StudentClass selectById(Integer id) {
		return studentClassMapper.selectById(id);
	}
	@Override
	public List<StudentClass> selectByLevelId(String id) {
		return studentClassMapper.selectByLevelId(id);
	}
	@Override
	public StudentClass selectByPrimaryKey(Integer id) {
		// TODO �Զ����ɵķ������
		return studentClassMapper.selectByPrimaryKey(id);
	}

	
	public StudentFall selectByPrimaryKeyOfFall(Integer id) {
		// TODO �Զ����ɵķ������
		return studentFallMapper.selectByPrimaryKey(id);
	}
	@Override
	public int countById(Integer id) {
		return studentClassMapper.countById(id);
	}
	//��ѯ�ð�ѧ���������
	public int findBigCode(String classno) {
		//���û�÷���ֵ(�༶��һ��ѧ�������ʱ)֮�䷵��01��
		String stuno = studentMapper.findBigCode(classno);
		int num = 0;
		if(null==stuno){
			logger.info("�����༶"+classno+"�ĵ�һ��ѧ��������0");
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
	//��ѯ��ǰ�����εķǱ�ҵ�༶
	public List<StudentClass> selectOnByClteac(String empId) {
		return studentClassMapper.selectOnByClteac(empId);
	}
	public List<StudentClass> selectTestCls(StudentClass cls) {
		return studentClassMapper.selectTestCls(cls);
	}
	public List<StudentClass> selectNormalCls(StudentClass cls) {
		return studentClassMapper.selectNormalCls(cls);
	}

}
