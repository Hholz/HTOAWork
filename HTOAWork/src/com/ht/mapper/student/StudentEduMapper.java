package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentEdu;

public interface StudentEduMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentEdu record);

    StudentEdu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentEdu record);

    int delByUpdate(Integer id);
    
    List<StudentEdu> selectAll();

	List<StudentEdu> selectBystuId(Integer stuId);
}