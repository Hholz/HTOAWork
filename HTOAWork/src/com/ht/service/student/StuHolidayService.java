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
	//ͨ��Ա��id���������ϵ����е�ѧ�������(�����˵�)
	List<StuHoliday> selectByEmpId(String empId);
	//ͨ��Ա��id���������ϵ����е�ѧ������٣�δ����ģ�
	List<StuHoliday> selectByEmpIdNodel(String empId);
}
