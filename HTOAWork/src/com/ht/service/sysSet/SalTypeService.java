package com.ht.service.sysSet;

import java.util.List;

import com.ht.popj.sysSet.FinanceSalarytypese;


public interface SalTypeService {

	int deleteById(Integer id);

    int insertByPJ(FinanceSalarytypese saltype);

    FinanceSalarytypese selectById(Integer id);

    int updateByPJ(FinanceSalarytypese saltype);
    
    int delByUpdate(Integer id);
    
    List<FinanceSalarytypese> selectAll();
}
