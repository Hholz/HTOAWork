package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentFloor;

public interface StudentFloorService {
	 int insert(StudentFloor record);
	 List<StudentFloor> selectStudentFloorAll();
	  //��̬��ѯ
	 List<StudentFloor> selectByDynamic(StudentFloor studentFloor);
	 //�޸�¥��
	 int updateByPrimaryKeySelective(StudentFloor record);
	 //�޸�¥��״̬
	 int updateStatusPrimaryKey(Integer record);
	 
	 int insertSelective(StudentFloor record);
}
