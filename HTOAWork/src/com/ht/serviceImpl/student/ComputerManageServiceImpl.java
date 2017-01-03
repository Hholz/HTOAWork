package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.ComputerManageMapper;
import com.ht.popj.student.ComputerManage;
import com.ht.service.student.ComputerManageService;

public class ComputerManageServiceImpl implements ComputerManageService{

	@Autowired
	ComputerManageMapper computerManageMapper;
	
	@Override
	public List<ComputerManage> computermanage(ComputerManage record) {
		// TODO Auto-generated method stub
		return computerManageMapper.computermanage(record);
	}

	@Override
	public int insertSelective(ComputerManage record) {
		// TODO Auto-generated method stub
		return computerManageMapper.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(ComputerManage record) {
		// TODO Auto-generated method stub
		return computerManageMapper.updateByPrimaryKeySelective(record);
	}

}
