package com.ht.serviceImpl.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.flow.WaitformaterialMapper;
import com.ht.popj.flow.Waitformaterial;
import com.ht.service.flow.WaitformaterialService;

public class WaitformaterialServiceImpl implements WaitformaterialService {
	
	@Autowired
	WaitformaterialMapper waitformaterialmapper;
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		waitformaterialmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(Waitformaterial record) {
//		int a = 1;
//		try {
			waitformaterialmapper.insertSelective(record);

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
	public Waitformaterial selectByPrimaryKey(Integer id) {
		
		return waitformaterialmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Waitformaterial> selectAll() {
		return waitformaterialmapper.selectAll();
	}

	@Override
	public List<Waitformaterial> selectSelective(Waitformaterial record) {
		return waitformaterialmapper.selectSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Waitformaterial record) {
//		int a = 1;
//		try {
		waitformaterialmapper.updateByPrimaryKeySelective(record);
//		} catch (Exception e) {
//			a = 0;
//			// record.toString();//日志输出
//		}
//		return a;
			return 1;
	}

}
