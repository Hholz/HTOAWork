package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FinancePolicquantityset;

public interface FinancePolicquantitysetMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(FinancePolicquantityset record);

    FinancePolicquantityset selectByPrimaryKey(Integer id);
    
    List<FinancePolicquantityset> selectAll();

	List<FinancePolicquantityset> selectSelective(FinancePolicquantityset record);

    int updateByPrimaryKeySelective(FinancePolicquantityset record);
    
    FinancePolicquantityset selectByMaterialid(Integer id);

}