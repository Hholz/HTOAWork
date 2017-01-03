package com.ht.mapper.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.StuHoliday;

public interface StuHolidayMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(StuHoliday record);

    StuHoliday selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StuHoliday record);
    
    int delByUpdate(Integer id);
    
    List<StuHoliday> selectAll();

    List<StuHoliday> selectByStuId(Integer stuId);
    //ͨ��ѧ��ids����status ��= 0
	List<StuHoliday> selectbyStuIds(Map map);
	//ͨ��ѧ��ids����status = 0
	List<StuHoliday> selectbyStuIdsNoDel(Map map);
}