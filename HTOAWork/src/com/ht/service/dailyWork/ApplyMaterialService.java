package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Emp;

public interface ApplyMaterialService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(ApplyMaterial record);

    ApplyMaterial selectByPrimaryKey(Integer id);
    
    List<ApplyMaterial> selectByName(ApplyMaterial record);
    
    List<ApplyMaterial> slelectflowschedul(ApplyMaterial record);
    
    List<ApplyMaterial> selectFinanceID(ApplyMaterial record);
    
    Integer selectTask(Emp record);
    
    int updateByPrimaryKeySelective(ApplyMaterial record);

}
