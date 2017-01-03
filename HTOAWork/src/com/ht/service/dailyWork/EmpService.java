package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Emp;

public interface EmpService {
    int deleteByPrimaryKey(String id);

    int insert(Emp record);//新增员工并生成账号

    Emp selectByPrimaryKey(String id);

    int updateByPrimaryKey(Emp record);
    
    List<Emp> selectEmp(Emp emp);
    
    Emp selectEmpById(String id);
    
    List<Emp> selectByDepid(Integer depid);
}
