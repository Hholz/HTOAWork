package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.FlowReceivematerialdetailMapper;
import com.ht.popj.flow.FlowReceivematerialdetail;
import com.ht.service.flow.FlowReceivematerialdetailService;

public class FlowReceivematerialdetailServiceImpl implements FlowReceivematerialdetailService {
	
	@Autowired
	FlowReceivematerialdetailMapper frematerialmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		frematerialmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(FlowReceivematerialdetail record) {
//		int a = 1;
//		try {
			frematerialmapper.insertSelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		if (a == 0) {
//			return a;
//		}
		return record.getId();
	}

	@Override
	public FlowReceivematerialdetail selectByPrimaryKey(Integer id) {
		return frematerialmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<FlowReceivematerialdetail> selectAll() {
		return frematerialmapper.selectAll();
	}

	@Override
	public List<FlowReceivematerialdetail> selectSelective(FlowReceivematerialdetail record) {
		return frematerialmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(FlowReceivematerialdetail record) {
//		int a = 1;
//		try {
			frematerialmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
			// record.toString();//日志输出
//		}
//		return a;	
		return 1;
	}

	@Override
	public List<FlowReceivematerialdetail> selectLength(Integer id) {
		return frematerialmapper.selectLength(id);
	}

	@Override
	public List<FlowReceivematerialdetail> selectReceiveMateriallist(String empid) {
		return frematerialmapper.selectReceiveMateriallist(empid);
	}

}
