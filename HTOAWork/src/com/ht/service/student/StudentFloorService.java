package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentFloor;

public interface StudentFloorService {
	 int insert(StudentFloor record);
	 List<StudentFloor> selectStudentFloorAll();
	  //¶¯Ì¬²éÑ¯
	 List<StudentFloor> selectByDynamic(StudentFloor studentFloor);
	 //ÐÞ¸ÄÂ¥¶°
	 int updateByPrimaryKeySelective(StudentFloor record);
	 //ÐÞ¸ÄÂ¥¶°×´Ì¬
	 int updateStatusPrimaryKey(Integer record);
	 
	 int insertSelective(StudentFloor record);
}
