package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Swapduty;

public interface SwapdutyService {
	//���������б�
	List<Swapduty> findSwapdutyList1(Swapduty swapduty);
	List<Swapduty> findSwapdutyList2();
	//����
	int addSwapduty(Swapduty swapduty);
	//�޸�
	int updateSwapduty(Swapduty swapduty);
	//ɾ������
	int deleteSwapdutyById(Integer id);
	
	List<Swapduty> selectByName(Swapduty swapduty);
	
	Swapduty selectByPrimaryKey(Integer id);
}
