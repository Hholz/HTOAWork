package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentFeedBack;

public interface StudentFeedBackMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentFeedBack record);

    int insertSelective(StudentFeedBack record);

    StudentFeedBack selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentFeedBack record);

    int updateByPrimaryKey(StudentFeedBack record);
    
    List<StudentFeedBack> selectAll();
    List<StudentFeedBack> selectDynamic(StudentFeedBack record);
    
    int selectCount();
}