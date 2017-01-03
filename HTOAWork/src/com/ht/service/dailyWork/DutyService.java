package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Duty;
import com.ht.popj.dailyWork.Emp;

public interface DutyService {
	//值班列表
	List<Duty> findDutyList1(Duty duty);
	List<Duty> findDutyList2();
	//新增值班
	int addDuty(Duty duty);
	//修改值班
	int updateDuty(Duty duty);
	//删除值班
	int deleteDuty(Integer id);
	//通过id查询信息
	Duty selectById(Integer id);
	//查询出所有的周数
	List<String> selectweeks(String dutyid);
	List<String> selectweeks2();
	//查询出周数对应的周几
	List<String> selectweekends(String weeks,String dutyid);
	List<String> selectweekends2(String weeks);
	//查询出周数对应的周几及其对应的值班人
	List<Emp> selectdutyid(String weeks,String weekends);
}
