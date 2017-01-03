package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentHourse;

public interface StudentHourseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentHourse record);

    int insertSelective(StudentHourse record);

    StudentHourse selectByPrimaryKey(Integer id);
    
    List<StudentHourse> findHouseList(StudentHourse house);
    
    int updateByPrimaryKeySelective(StudentHourse record);

    int updateByPrimaryKey(StudentHourse record);
    
    List<StudentHourse> selectDynamic(StudentHourse record);
}