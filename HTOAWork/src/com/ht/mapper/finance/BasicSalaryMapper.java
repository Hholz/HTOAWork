package com.ht.mapper.finance;

import java.util.List;

import com.ht.popj.finance.BasicSalary;

public interface BasicSalaryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BasicSalary record);

    int insertSelective(BasicSalary record);

    BasicSalary selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BasicSalary record);

    int updateByPrimaryKey(BasicSalary record);
    
    List<BasicSalary> selectByDynamic(BasicSalary record);
}