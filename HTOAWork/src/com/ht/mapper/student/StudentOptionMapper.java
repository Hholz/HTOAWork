package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentOption;

public interface StudentOptionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentOption record);

    int insertSelective(StudentOption record);

    StudentOption selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentOption record);

    int updateByPrimaryKey(StudentOption record);
    
    List<StudentOption> selectDynamic(StudentOption record);
}