package com.ht.serviceImpl.dailyWork;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.dailyWork.AttendancecountMapper;
import com.ht.popj.dailyWork.Attendancecount;
import com.ht.service.dailyWork.AttendancecountService;

public class AttendancecountServiceImpl implements AttendancecountService{
	@Autowired
	AttendancecountMapper attcountmapper;
	
	
	@Override
	public int deleteByPrimaryKey(Integer id) {
		attcountmapper.deleteByPrimaryKey(id);
		return 1;
	}

	@Override
	public int insertSelective(Attendancecount record) {
		attcountmapper.insertSelective(record);
		return record.getId();
	}

	@Override
	public Attendancecount selectByPrimaryKey(Integer id) {
		return attcountmapper.selectByPrimaryKey(id);
	}

	@Override
	public List<Attendancecount> selectByName(Attendancecount id) {
		return attcountmapper.selectByName(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Attendancecount record) {
		return attcountmapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Integer selecthickcount(Attendancecount id) {
		// TODO Auto-generated method stub
		return attcountmapper.selecthickcount(id);
	}

	@Override
	public Integer selectthingcount(Attendancecount id) {
		// TODO Auto-generated method stub
		return attcountmapper.selectthingcount(id);
	}

	@Override
	public Integer selecttovercount(Attendancecount id) {
		// TODO Auto-generated method stub
		return attcountmapper.selecttovercount(id);
	}

	@Override
	public Integer selecttlatecount(Attendancecount id) {
		// TODO Auto-generated method stub
		return attcountmapper.selecttlatecount(id);
	}

	@Override
	public Integer selecttmissworkcount(Attendancecount id) {
		// TODO Auto-generated method stub
		return attcountmapper.selecttmissworkcount(id);
	}

	@Override
	public Integer selecttdutycount(Attendancecount id) {
		// TODO Auto-generated method stub
		return attcountmapper.selecttdutycount(id);
	}

}
