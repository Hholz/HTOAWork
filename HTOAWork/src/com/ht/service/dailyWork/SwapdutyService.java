package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Swapduty;

public interface SwapdutyService {
	//调班申请列表
	List<Swapduty> findSwapdutyList1(Swapduty swapduty);
	List<Swapduty> findSwapdutyList2();
	//新增
	int addSwapduty(Swapduty swapduty);
	//修改
	int updateSwapduty(Swapduty swapduty);
	//删除申请
	int deleteSwapdutyById(Integer id);
	
	List<Swapduty> selectByName(Swapduty swapduty);
	
	Swapduty selectByPrimaryKey(Integer id);
}
