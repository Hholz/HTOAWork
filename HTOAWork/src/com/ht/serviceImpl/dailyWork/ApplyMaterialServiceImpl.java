package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.ApplyMaterialMapper;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.ApplyMaterialService;

public class ApplyMaterialServiceImpl implements ApplyMaterialService{
	
	@Autowired
	ApplyMaterialMapper appmaterilmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		appmaterilmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(ApplyMaterial record) {
		appmaterilmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public ApplyMaterial selectByPrimaryKey(Integer id) {
		return appmaterilmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<ApplyMaterial> selectByName(ApplyMaterial record) {
		
		return appmaterilmapper.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ApplyMaterial record) {
		appmaterilmapper.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public List<ApplyMaterial> slelectflowschedul(ApplyMaterial record) {
		return appmaterilmapper.slelectflowschedul(record);
	}

	@Override
	public List<ApplyMaterial> selectFinanceID(ApplyMaterial record) {
		return appmaterilmapper.selectFinanceID(record);
	}

	@Override
	public Integer selectTask(Emp record) {
		return appmaterilmapper.selectTask(record);
	}

}
