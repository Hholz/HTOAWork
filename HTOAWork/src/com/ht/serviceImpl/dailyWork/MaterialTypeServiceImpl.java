package com.ht.serviceImpl.dailyWork;

import com.ht.service.dailyWork.MaterialTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.ht.mapper.dailyWork.MaterialTypeMapper;
import com.ht.popj.dailyWork.MaterialType;


public class MaterialTypeServiceImpl implements MaterialTypeService{

	@Autowired
	MaterialTypeMapper materialtypeMapper;
	
	@Override
	public int addMaterialType(MaterialType materialtype) {
		materialtypeMapper.insertSelective(materialtype);
		return materialtype.getId();
	}

	@Override
	public void updateMaterialType(MaterialType materialtype) {
		materialtypeMapper.updateByPrimaryKeySelective(materialtype);
	}

	@Override
	public void deleteMaterialType(int id) {
		materialtypeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<MaterialType> findListMaterialType(MaterialType materialtype) {
		List<MaterialType> materialtypelist = null;
		if(materialtype.getMaterialtypename().equals("") && materialtype.getMatertypeRemark().equals("")){
			materialtypelist = materialtypeMapper.selectList();
		}else{
			materialtypelist = materialtypeMapper.selectByName(materialtype);
		}
		return materialtypelist;
	}

	@Override
	public MaterialType findById(int id) {
		MaterialType m = materialtypeMapper.selectByPrimaryKey(id);
		return m;
	}

	@Override
	public List<MaterialType> selectList() {
		
		return materialtypeMapper.selectList();
	}

}
