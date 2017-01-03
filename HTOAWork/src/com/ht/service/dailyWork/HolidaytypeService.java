package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.HolidayType;

public interface HolidaytypeService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(HolidayType record);

    HolidayType selectByPrimaryKey(Integer id);
    
    List<HolidayType> selectList();
    
    List<HolidayType> selectByName(HolidayType name);

    int updateByPrimaryKeySelective(HolidayType record);

}
