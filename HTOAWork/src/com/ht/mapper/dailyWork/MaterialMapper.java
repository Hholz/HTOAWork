package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Material;

public interface MaterialMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(Material record);

    Material selectByPrimaryKey(Integer id);
    
    List<Material> selectList();
    
    List<Material> selectMaterial(Material record);
    
    List<Material> selectByName(String name);

    int updateByPrimaryKeySelective(Material record);
    
    List<Material> selectMaterialByName();
}