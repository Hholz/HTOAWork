package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowRoleMapper;
import com.ht.popj.flow.FlowRole;
import com.ht.service.flow.FlowRoleService;

public class FlowRoleServiceImpl implements FlowRoleService {

	@Autowired
	FlowRoleMapper mapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		mapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insert(FlowRole record) {
		mapper.insert(record);
		return 1;
	}

	@Override
	public int insertSelective(FlowRole record) {
		int a = 1;
		try {
			mapper.insertSelective(record);

		} catch (Exception e) {
			a = 0;
			// record.toString();//»’÷æ ‰≥ˆ
		}
		if (a == 0) {
			return a;
		}
		return record.getId();
	}

	@Override
	public FlowRole selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowRole record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FlowRole record) {
		mapper.updateByPrimaryKey(record);
		return 1;
	}

	@Override
	public List<FlowRole> selectAll() {
		return mapper.selectAll();
	}

}
