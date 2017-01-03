package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.MaterialType;
public interface MaterialTypeService {
	
	//新增
	public int addMaterialType(MaterialType materialtype);
	//修改
	public void updateMaterialType(MaterialType materialtype);
	//删除
	public void deleteMaterialType(int id);
	//查询List
	public List<MaterialType> findListMaterialType(MaterialType materialtype);
	//主键查询
	public MaterialType findById(int id);
	
	List<MaterialType> selectList();
}
