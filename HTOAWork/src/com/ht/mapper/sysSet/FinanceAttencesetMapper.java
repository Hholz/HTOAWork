package com.ht.mapper.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FinanceAttenceset;

public interface FinanceAttencesetMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(FinanceAttenceset record);

    int insertSelective(FinanceAttenceset record);

    FinanceAttenceset selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(FinanceAttenceset record);

    int updateByPrimaryKey(FinanceAttenceset record);
    
    List<FinanceAttenceset> financeattensel(FinanceAttenceset record);
    
    List<FinanceAttenceset> selectidnull(int id);
    
    FinanceAttenceset selectabjuct();
}