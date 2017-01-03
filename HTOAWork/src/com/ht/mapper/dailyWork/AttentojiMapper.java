package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Attentoji;

public interface AttentojiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Attentoji record);

    int insertSelective(Attentoji record);

    Attentoji selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Attentoji record);

    int updateByPrimaryKey(Attentoji record);
    
    List<Attentoji> Attentojiselect(Attentoji record);
}