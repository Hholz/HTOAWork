package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentAttencecond;
/*
 * ѧ������״̬�������
 */
public interface StudentAttencondService {

	public List<StudentAttencecond> selectByPrimaryKeyall();
	
	public int insertSelective(StudentAttencecond record);
	
	public int updateByPrimaryKeySelective(StudentAttencecond record);
}
