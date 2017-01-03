package com.ht.service.finance;

import java.util.ArrayList;
import java.util.List;

import com.ht.popj.finance.SalaryDetail;

public interface SalaryDetailService {

    int deleteByPrimaryKey(Integer id);
    
    int deleteBySalaryId(Integer salaryid);

    int insert(SalaryDetail record);

    int insertSelective(SalaryDetail record);

    SalaryDetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SalaryDetail record);

    int updateByPrimaryKey(SalaryDetail record);
    
    List<SalaryDetail> selectByDynamic(SalaryDetail record);
    
    int insertMuch(ArrayList<SalaryDetail> salArr);
}
