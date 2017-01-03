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
	
	public List<StudentClass> selectStudentclass(StudentClass cls);//查所有班
	public List<StudentClass> selectNormalCls(StudentClass cls);//查所有正常班
	public List<StudentClass> selectTestCls(StudentClass cls);//查所有试学班
	
	public List<StudentClass> selectStudentclass2(int id);
	
	public int updateByPrimaryKeySelective(StudentClass record);
	
	public List<StudentFall> selectStufallclass(int id);
	
	public List<StudentFall> selectStufallclasstwo();
	
	public List<StudentClass> selectallstduentclass();
	
	//通过id查班级并包含该班级的stuList
	public StudentClass selectById(Integer id);
	//通过届别id查学生
	List<StudentClass> selectByLevelId(String id);
	
	public StudentClass selectByPrimaryKey(Integer id);
	
	public StudentFall selectByPrimaryKeyOfFall(Integer id);

	public int countById(Integer id);//查询该班有多少人
	
	public int findBigCode(String classno);//查询该班学生的最大编号
	//查询当前班主任的非毕业班级
	public List<StudentClass> selectOnByClteac(String empId);
}
