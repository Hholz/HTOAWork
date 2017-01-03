package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.DutyMapper;
import com.ht.popj.dailyWork.Duty;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.DutyService;

public class DutyServiceImpl implements DutyService{
	@Autowired
	private DutyMapper dutyMapper;

	@Override
	public List<Duty> findDutyList1(Duty duty) {
		return dutyMapper.findDutyList1(duty);
	}

	@Override
	public List<Duty> findDutyList2() {
		return dutyMapper.findDutyList2();
	}

	@Override
	public int addDuty(Duty duty) {
		return dutyMapper.addDuty(duty);
	}

	@Override
	public int updateDuty(Duty duty) {
		return dutyMapper.updateDuty(duty);
	}

	@Override
	public int deleteDuty(Integer id) {
		return dutyMapper.deleteDuty(id);
	}

	@Override
	public Duty selectById(Integer id) {
		return dutyMapper.selectById(id);
	}

	@Override
	public List<String> selectweeks(String dutyid) {
		return dutyMapper.selectweeks(dutyid);
	}

	@Override
	public List<String> selectweekends(String weeks,String dutyid) {
		return dutyMapper.selectweekends(weeks,dutyid);
	}

	@Override
	public List<String> selectweeks2() {
		return dutyMapper.selectweeks2();
	}

	@Override
	public List<String> selectweekends2(String weeks) {
		return dutyMapper.selectweekends2(weeks);
	}

	@Override
	public List<Emp> selectdutyid(String weeks, String weekends) {
		return dutyMapper.selectdutyid(weeks, weekends);
	}

}
