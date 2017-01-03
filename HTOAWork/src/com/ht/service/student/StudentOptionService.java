package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.Student;
import com.ht.popj.student.StudentOption;

public interface StudentOptionService {

	int deleteByPrimaryKey(Integer id);
	int insertSelective(StudentOption record);

	List<StudentOption> selectDynamic(StudentOption record);
}
