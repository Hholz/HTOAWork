package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentComputerApply;

public interface StudentComputerApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentComputerApply record);

    int insertSelective(StudentComputerApply record);

    StudentComputerApply selectByPrimaryKey(Integer id);
    
    
    Integer selectNewTask(Emp e);
    
    Integer selectRepairTask(Emp e);
    
    Integer selectReviceTask(Emp e);
    
    Integer selectReturnTask(Emp e);

    int updateByPrimaryKeySelective(StudentComputerApply record);

    int updateByPrimaryKey(StudentComputerApply record);
    
    List<StudentComputerApply> studentcomputermanage(StudentComputerApply record);
}