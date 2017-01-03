package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentAttencecond;

public interface StudentAttencecondMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentAttencecond record);

    int insertSelective(StudentAttencecond record);

    StudentAttencecond selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentAttencecond record);

    int updateByPrimaryKey(StudentAttencecond record);
    
    List<StudentAttencecond> selectByPrimaryKeyall();
}