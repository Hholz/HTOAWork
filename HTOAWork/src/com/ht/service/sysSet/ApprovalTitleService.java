package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.ApprovalTitle;

public interface ApprovalTitleService {
	int deleteByPrimaryKey(Integer id);

    int insertSelective(ApprovalTitle record);

    ApprovalTitle selectByPrimaryKey(Integer id);
    
    List<ApprovalTitle> selectByName(ApprovalTitle record);

    int updateByPrimaryKeySelective(ApprovalTitle record);
}
