package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.ReturnMaterial;

public interface ReturnMaterialService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(ReturnMaterial record);

	ReturnMaterial selectByPrimaryKey(Integer id);

	List<ReturnMaterial> selectByName(ReturnMaterial record);
	
	Integer selectTask(Emp record);

	int updateByPrimaryKeySelective(ReturnMaterial record);

}
