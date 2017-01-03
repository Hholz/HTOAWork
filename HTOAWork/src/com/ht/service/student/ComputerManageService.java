package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.ComputerManage;

public interface ComputerManageService {
	
	public List<ComputerManage> computermanage(ComputerManage record);
	
	public int insertSelective(ComputerManage record);

	public int updateByPrimaryKeySelective(ComputerManage record);
}
