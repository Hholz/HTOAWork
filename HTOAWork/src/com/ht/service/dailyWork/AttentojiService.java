package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Attentoji;

public interface AttentojiService {

	int insertSelective(Attentoji record);
	
	List<Attentoji> Attentojiselect(Attentoji record);
	
	int updateByPrimaryKeySelective(Attentoji record);
}
