package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.MaterialMapper;
import com.ht.popj.dailyWork.Material;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.service.dailyWork.MaterialService;

public class MaterialServiceImpl implements MaterialService{
	
	@Autowired
	MaterialMapper matermapper;

	@Override
	public int deleteByPrimaryKey(Integer id) {
		matermapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(Material record) {
		matermapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public Material selectByPrimaryKey(Integer id) {
		return matermapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Material> selectList() {
		return matermapper.selectList();
	}

	@Override
	public List<Material> selectByName(Material record) {
		return matermapper.selectMaterial(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Material record) {
		matermapper.updateByPrimaryKeySelective(record);
		return 1;
	}

}
