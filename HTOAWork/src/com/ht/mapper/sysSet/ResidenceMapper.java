package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.Residence;

public interface ResidenceMapper {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(Residence record);

    Residence selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Residence record);
    
    int delByUpdate(Integer id);
    
    List<Residence> selectAll();
}