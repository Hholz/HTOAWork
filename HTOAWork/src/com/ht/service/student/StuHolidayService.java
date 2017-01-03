package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuHoliday;

public interface StuHolidayService {

	int deleteById(Integer id);

    int insertByPJ(StuHoliday stuHoliday);

    StuHoliday selectById(Integer id);

    int updateByPJ(StuHoliday stuHoliday);
    
    int delByUpdate(Integer id);
    
    List<StuHoliday> selectAll();

	List<StuHoliday> selectByStuId(Integer stuId);
	//通过员工id来查他班上的所有的学生的请假(处理了的)
	List<StuHoliday> selectByEmpId(String empId);
	//通过员工id来查他班上的所有的学生的请假（未处理的）
	List<StuHoliday> selectByEmpIdNodel(String empId);
}
