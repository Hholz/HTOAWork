package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Attendancecount;

public interface AttendancecountMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Attendancecount record);

    Attendancecount selectByPrimaryKey(Integer id);
    
    List<Attendancecount> selectByName(Attendancecount id);
    
    Integer selecthickcount(Attendancecount id);
    
    Integer selectthingcount(Attendancecount id);
    
    Integer selecttovercount(Attendancecount id);
    
    Integer selecttlatecount(Attendancecount id);
    
    Integer selecttmissworkcount(Attendancecount id);
    
    Integer selecttdutycount(Attendancecount id);

    int updateByPrimaryKeySelective(Attendancecount record);

}