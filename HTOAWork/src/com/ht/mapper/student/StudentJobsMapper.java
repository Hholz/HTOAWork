package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentJobs;

public interface StudentJobsMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StudentJobs record);

    StudentJobs selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentJobs record);

    int delByUpdate(Integer id);
    
    List<StudentJobs> selectAll();
    
    StudentJobs selectByStuId(Integer id);
}