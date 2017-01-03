package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentScore;

public interface StudentScoreService {

	List<StudentScore> selectAll();
	List<StudentScore> selectDinamic(StudentScore score);
	int insert(StudentScore record);
    int insertSelective(StudentScore record);
    int deleteByPrimaryKey(Integer id);
    int updateByPrimaryKey(StudentScore record);
    int updateByPrimaryKeySelective(StudentScore record);

}
