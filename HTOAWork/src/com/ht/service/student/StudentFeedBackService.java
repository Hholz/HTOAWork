package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentFeedBack;

public interface StudentFeedBackService {

	int insert(StudentFeedBack feed);
	List<StudentFeedBack> selectAll();
	List<StudentFeedBack> slectDynamic(StudentFeedBack stu);
	int selectCount();
	int updateByPrimaryKeySelective(StudentFeedBack record);
}
