package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Attendances;

public interface AttendancesMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attendances record);

    int insertSelective(Attendances record);

    Attendances selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attendances record);

    int updateByPrimaryKey(Attendances record);
    
    List<Attendances> attendanceselect(Attendances record);
}