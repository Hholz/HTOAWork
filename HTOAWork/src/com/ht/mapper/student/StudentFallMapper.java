package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentFall;

public interface StudentFallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentFall record);
    
    List<StudentFall> selectFallAllList();

    void insertSelective(StudentFall record);

    StudentFall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentFall record);

    int updateByPrimaryKey(StudentFall record);
    
    List<StudentFall> selectStudentFall();
    
    List<StudentFall> selectByDynamic(StudentFall record);
    
    List<StudentFall> selectStufallclass(Integer id);
    
    List<StudentFall> selectStufallclasstwo();
    
    List<StudentFall> selectFallList();
    
    List<StudentFall> selectFallByComputer();
}