package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Emp;

public interface EmpMapper {
    int deleteByPrimaryKey(String id);

    int insert(Emp record);

    Emp selectByPrimaryKey(String id);

    int updateByPrimaryKey(Emp record);
    
    List<Emp> selectEmp(Emp emp);
    
    Emp selectEmpById(String id);
    
    List<Emp> selectByDepid(Integer depid);
    
    List<Emp> selectEmpName(int depid);
}