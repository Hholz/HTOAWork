package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentAttencecond;
/*
 * 学生考勤状态相关设置
 */
public interface StudentAttencondService {

	public List<StudentAttencecond> selectByPrimaryKeyall();
	
	public int insertSelective(StudentAttencecond record);
	
	public int updateByPrimaryKeySelective(StudentAttencecond record);
}
