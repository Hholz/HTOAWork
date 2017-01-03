package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentFloor;

public interface StudentFloorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentFloor record);

    int insertSelective(StudentFloor record);

    StudentFloor selectByPrimaryKey(Integer id);
    //ÐÞ¸ÄÂ¥¶°
    int updateByPrimaryKeySelective(StudentFloor record);

    int updateByPrimaryKey(StudentFloor record);
    
    List<StudentFloor> selectStudentFloorAll();
    //¶¯Ì¬²éÑ¯
    List<StudentFloor> selectByDynamic(StudentFloor studentFloor);
    
    //ÐÞ¸ÄÂ¥¶°×´Ì¬
    int updateStatusPrimaryKey(Integer id);
}