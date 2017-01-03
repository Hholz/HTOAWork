package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Material;

public interface MaterialService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(Material record);

	Material selectByPrimaryKey(Integer id);

	List<Material> selectList();

	List<Material> selectByName(Material record);

	int updateByPrimaryKeySelective(Material record);
}
