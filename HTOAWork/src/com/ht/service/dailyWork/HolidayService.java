package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Holiday;

public interface HolidayService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(Holiday record);

	Holiday selectByPrimaryKey(Integer id);

	List<Holiday> selectByName(Holiday record);

	List<Holiday> selectHoliday(Holiday record);

	Integer selectTask(Emp e);

	Integer selectOverTask(Emp e);

	Integer selectChangeTask(Emp e);

	int updateByPrimaryKeySelective(Holiday record);

}
