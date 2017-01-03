package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StudentRoom;

public interface StudentRoomService {
	
	int deleteByPrimaryKey(Integer id);

    int insert(StudentRoom record);

    int insertSelective(StudentRoom record);

    StudentRoom selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentRoom record);

    int updateByPrimaryKey(StudentRoom record);
	List<StudentRoom> selectDynamic(StudentRoom record);
}
