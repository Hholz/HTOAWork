package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.SwapdutyMapper;
import com.ht.popj.dailyWork.Swapduty;
import com.ht.service.dailyWork.SwapdutyService;

public class SwapdutyServiceImpl implements SwapdutyService{
	@Autowired
	private SwapdutyMapper swapMapper;

	@Override
	public List<Swapduty> findSwapdutyList1(Swapduty swapduty) {
		return swapMapper.findSwapdutyList1(swapduty);
	}

	@Override
	public List<Swapduty> findSwapdutyList2() {
		return swapMapper.findSwapdutyList2();
	}

	@Override
	public int addSwapduty(Swapduty swapduty) {
		return swapMapper.addSwapduty(swapduty);
	}

	@Override
	public int updateSwapduty(Swapduty swapduty) {
		return swapMapper.updateSwapduty(swapduty);
	}

	@Override
	public int deleteSwapdutyById(Integer id) {
		return swapMapper.deleteSwapdutyById(id);
	}

	@Override
	public List<Swapduty> selectByName(Swapduty swapduty) {
		return swapMapper.selectByName(swapduty);
	}

	@Override
	public Swapduty selectByPrimaryKey(Integer id) {
		return swapMapper.selectByPrimaryKey(id);
	}

}
