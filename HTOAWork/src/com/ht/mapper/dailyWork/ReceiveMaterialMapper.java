package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.ReceiveMaterial;

public interface ReceiveMaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ReceiveMaterial record);

    ReceiveMaterial selectByPrimaryKey(Integer id);
    
    List<ReceiveMaterial> selectByName(ReceiveMaterial record);
    
    Integer selectTask(Emp record);

    int updateByPrimaryKeySelective(ReceiveMaterial record);

}