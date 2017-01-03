package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentJobs;


public interface StuJobService {

	int deleteById(Integer id);

    int insertByPJ(StudentJobs studentJob);

    StudentJobs selectById(Integer id);

    int updateByPJ(StudentJobs studentJob);
    
    int delByUpdate(Integer id);
    
    List<StudentJobs> selectAll();
    
    StudentJobs selectByStuId(Integer id);
}
