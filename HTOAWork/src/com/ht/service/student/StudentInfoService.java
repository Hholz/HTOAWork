package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;

public interface StudentInfoService {

	public List<StudentFall> selectStudentFall() throws Exception;
	
	public void insertSelective(StudentFall record) throws Exception;
	
	public int updateByPrimaryKeySelective(StudentFall record);
	
	public List<StudentFall> selectByDynamic(StudentFall record);
	
	public  int insertSelective(StudentClass record);
	
	public List<StudentClass> selectStudentclass(StudentClass cls);//�����а�
	public List<StudentClass> selectNormalCls(StudentClass cls);//������������
	public List<StudentClass> selectTestCls(StudentClass cls);//��������ѧ��
	
	public List<StudentClass> selectStudentclass2(int id);
	
	public int updateByPrimaryKeySelective(StudentClass record);
	
	public List<StudentFall> selectStufallclass(int id);
	
	public List<StudentFall> selectStufallclasstwo();
	
	public List<StudentClass> selectallstduentclass();
	
	//ͨ��id��༶�������ð༶��stuList
	public StudentClass selectById(Integer id);
	//ͨ�����id��ѧ��
	List<StudentClass> selectByLevelId(String id);
	
	public StudentClass selectByPrimaryKey(Integer id);
	
	public StudentFall selectByPrimaryKeyOfFall(Integer id);

	public int countById(Integer id);//��ѯ�ð��ж�����
	
	public int findBigCode(String classno);//��ѯ�ð�ѧ���������
	//��ѯ��ǰ�����εķǱ�ҵ�༶
	public List<StudentClass> selectOnByClteac(String empId);
}
