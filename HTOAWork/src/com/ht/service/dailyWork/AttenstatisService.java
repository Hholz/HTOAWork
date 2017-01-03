package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.dailyWork.Attenstatis;

public interface AttenstatisService {
	
	int insertSelective(Attenstatis record);
	
	int updateByPrimaryKeySelective(Attenstatis record);
	
	List<Attenstatis> Attenstatiselect(Attenstatis record);
	
	List<Attenstatis> Attenstatiselandtoji(Attenstatis record);
	
	Attenstatis  findattenstatis(Attenstatis attenstatis)throws Exception;

	void deleteByPrimaryKey2(Attenstatis record);
	
	int addinsertlist(List<Attendance_tal> Attendance_tal);
	
	List<Attenstatis> attenrewardsel(Attenstatis record);
}
