package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentLayer;

public interface StudentLayerService {
	 int insert(StudentLayer record);
	 int insertSelective(StudentLayer record);
	 List<StudentLayer> selectStudentLayerAll();
	 
	 int updateLayerStatusByPrimaryKey(Integer id);
	 
	 List<StudentLayer> selectStudentLayerNameAll();
	 List<StudentLayer> selectDynamic(StudentLayer record);
}
