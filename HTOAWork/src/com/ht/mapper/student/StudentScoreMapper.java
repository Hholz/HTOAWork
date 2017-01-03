package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentScore;

public interface StudentScoreMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentScore record);

    int insertSelective(StudentScore record);

    StudentScore selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentScore record);

    int updateByPrimaryKey(StudentScore record);
    
    List<StudentScore> selectAll();
	List<StudentScore> selectDinamic(StudentScore score);

}