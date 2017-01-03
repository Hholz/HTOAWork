package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentEdu;


public interface StudentEduService {

	int deleteById(Integer id);

    int insertByPJ(StudentEdu studentEdu);

    StudentEdu selectById(Integer id);

    int updateByPJ(StudentEdu studentEdu);
    
    int delByUpdate(Integer id);
    
    List<StudentEdu> selectAll();
    
    List<StudentEdu> selectBystuId(Integer stuId);
}
