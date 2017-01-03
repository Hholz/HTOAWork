package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.dailyWork.Attenstatis;

public interface AttenstatisMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attenstatis record);

    int insertSelective(Attenstatis record);

    Attenstatis selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attenstatis record);

    int updateByPrimaryKey(Attenstatis record);
    
    List<Attenstatis> Attenstatiselect(Attenstatis record);
    
    List<Attenstatis> Attenstatiselandtoji(Attenstatis record);
    
    Attenstatis  findattenstatis(Attenstatis attenstatis)throws Exception;
    
    void deleteByPrimaryKey2(Attenstatis record);
    
    int addinsertlist(List<Attendance_tal> Attendance_tal);
    
    List<Attenstatis> attenrewardsel(Attenstatis attenstatis);

}