package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.ApproveType;

public interface ApproveTypeService {
	int deleteByPrimaryKey(Integer id);

	int insertSelective(ApproveType record);

	List<ApproveType> selectByPrimaryKey(Integer id);

	List<ApproveType> selectByName(ApproveType record);

	int updateByPrimaryKeySelective(ApproveType record);
}
