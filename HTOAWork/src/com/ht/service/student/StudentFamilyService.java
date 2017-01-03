package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentFamily;


public interface StudentFamilyService {

	int deleteById(Integer id);

    int insertByPJ(StudentFamily studentFam);

    StudentFamily selectById(Integer id);

    int updateByPJ(StudentFamily studentFam);
    
    int delByUpdate(Integer id);
    
    List<StudentFamily> selectAll();

	List<StudentFamily> selectBystuId(Integer stuId);
}
