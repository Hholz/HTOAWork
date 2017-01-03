package com.ht.service.student;

import java.util.List;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentComputerApply;

public interface StudentComputerService {
	
	public List<StudentComputerApply> studentcomputermanage(StudentComputerApply record);
	
	StudentComputerApply selectById(Integer id);
	
	public int updateByPrimaryKeySelective(StudentComputerApply record);

	public int insertSelective(StudentComputerApply record);
	
    Integer selectNewTask(Emp e);
    
    Integer selectRepairTask(Emp e);
    
    Integer selectReviceTask(Emp e);
    
    Integer selectReturnTask(Emp e);
}
