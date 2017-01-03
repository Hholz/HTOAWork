package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.MaterialType;

public interface MaterialTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(MaterialType record);

    MaterialType selectByPrimaryKey(Integer id);

    List<MaterialType> selectByName(MaterialType materialtype);

    List<MaterialType> selectList();

    int updateByPrimaryKeySelective(MaterialType record);

}