package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Familyinfo;

public interface FamilyinfoService {
    int deleteByPrimaryKey(Integer id);

    int insert(Familyinfo record);

    Familyinfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Familyinfo record);
    
    List<Familyinfo> selectFamilyinfo(Familyinfo record);
}
