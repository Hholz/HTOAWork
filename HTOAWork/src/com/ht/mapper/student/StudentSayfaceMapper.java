package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentSayface;

public interface StudentSayfaceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentSayface record);

    int insertSelective(StudentSayface record);

    StudentSayface selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentSayface record);

    int updateByPrimaryKey(StudentSayface record);
    
    List<StudentSayface> studentsayheart(StudentSayface record);
}