package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.HolidayType;

public interface HolidayTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(HolidayType record);

    int insertSelective(HolidayType record);

    HolidayType selectByPrimaryKey(Integer id);
    
    List<HolidayType> selectList();
    
    List<HolidayType> selectByName(HolidayType name);

    int updateByPrimaryKeySelective(HolidayType record);

    int updateByPrimaryKey(HolidayType record);
}