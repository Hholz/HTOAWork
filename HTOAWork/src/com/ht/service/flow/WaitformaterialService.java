package com.ht.service.flow;

import java.util.List;

import com.ht.popj.flow.Waitformaterial;

public interface WaitformaterialService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(Waitformaterial record);

    Waitformaterial selectByPrimaryKey(Integer id);
    
    List<Waitformaterial> selectAll();
    
    List<Waitformaterial> selectSelective(Waitformaterial record);

    int updateByPrimaryKeySelective(Waitformaterial record);
}
