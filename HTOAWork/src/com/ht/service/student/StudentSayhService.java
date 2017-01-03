package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentSayface;

public interface StudentSayhService {
	
	public List<StudentSayface> studentsayheart(StudentSayface record);

	public int insertSelective(StudentSayface record);
	
	public int updateByPrimaryKeySelective(StudentSayface record);
}
