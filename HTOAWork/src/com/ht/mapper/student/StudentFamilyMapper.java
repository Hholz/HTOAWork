package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentFamily;

public interface StudentFamilyMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentFamily record);

    StudentFamily selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentFamily record);

    int delByUpdate(Integer id);
    
    List<StudentFamily> selectAll();

	List<StudentFamily> selectBystuId(Integer stuId);
}