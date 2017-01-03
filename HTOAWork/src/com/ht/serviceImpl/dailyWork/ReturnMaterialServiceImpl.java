package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.ReturnMaterialMapper;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.ReturnMaterial;
import com.ht.service.dailyWork.ReturnMaterialService;

public class ReturnMaterialServiceImpl implements ReturnMaterialService {
	@Autowired
	ReturnMaterialMapper rmm;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		rmm.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(ReturnMaterial record) {
		rmm.insertSelective(record);
		return record.getId();
	}

	@Override
	public ReturnMaterial selectByPrimaryKey(Integer id) {
		return rmm.selectByPrimaryKey(id);
	}

	@Override
	public List<ReturnMaterial> selectByName(ReturnMaterial record) {
		return rmm.selectByName(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ReturnMaterial record) {
		rmm.updateByPrimaryKeySelective(record);
		return 1;
	}

	@Override
	public Integer selectTask(Emp record) {
		return rmm.selectTask(record);
	}

}
