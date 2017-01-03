package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.ApprovalTitle;

public interface ApprovalTitleMapper {
    int deleteByPrimaryKey(Integer id);

    int insertSelective(ApprovalTitle record);

    ApprovalTitle selectByPrimaryKey(Integer id);
    
    List<ApprovalTitle> selectByName(ApprovalTitle record);

    int updateByPrimaryKeySelective(ApprovalTitle record);

}