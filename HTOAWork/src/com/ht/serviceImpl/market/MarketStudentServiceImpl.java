package com.ht.serviceImpl.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.market.MarketStudentMapper;
import com.ht.popj.market.MarketStudent;
import com.ht.popj.market.MarketStudentCount;
import com.ht.service.market.MarketStudentService;

public class MarketStudentServiceImpl implements MarketStudentService{

	@Autowired
	MarketStudentMapper marketStudentMapper;
	public int deleteById(Integer id) {
		return marketStudentMapper.deleteByPrimaryKey(id);
	}

	public int insertByPJ(MarketStudent marketStudent) {
		return marketStudentMapper.insertSelective(marketStudent);
	}

	public MarketStudent selectById(Integer id) {
		return marketStudentMapper.selectByPrimaryKey(id);
	}

	public int updateByPJ(MarketStudent marketStudent) {
		return marketStudentMapper.updateByPrimaryKeySelective(marketStudent);
	}

	public List<MarketStudent> selectAll() {
		return marketStudentMapper.selectAll();
	}

	public List<MarketStudent> selectAllByPJ(MarketStudent marketStudent) {
		return marketStudentMapper.selectAllByPJ(marketStudent);
	}

	public int updateMsStatusById(Integer id,Integer msStatus) {
		Map map = new HashMap();
		map.put("id", id);
		map.put("msStatus", msStatus);
		return marketStudentMapper.updateMsStatusById(map);
	}
	//查出意向学生页面所显示的页面0，1状态
	public List<MarketStudent> selectFollowStudent(MarketStudent marketStudent) {
		Map map = new HashMap();
		map.put("marketStudent", marketStudent);
		List<Integer> msStatus = new ArrayList<Integer>();
		msStatus.add(0);
		msStatus.add(1);
		map.put("msStatus",msStatus);
		return marketStudentMapper.selectBymsStatusMap(map);
	}

	@Override
	public List<MarketStudent> selectIntentionStudent(MarketStudent marketStudent) {
		Map map = new HashMap();
		map.put("marketStudent", marketStudent);
		List<Integer> msStatus = new ArrayList<Integer>();
		msStatus.add(0);
		msStatus.add(1);
		msStatus.add(2);
		msStatus.add(3);
		msStatus.add(4);
		msStatus.add(5);
		map.put("msStatus",msStatus);
		return marketStudentMapper.selectBymsStatusMap(map);
	}

	@Override
	public List<MarketStudent> selectPredStudentAll(MarketStudent marketStudent) {
		Map map = new HashMap();
		map.put("marketStudent", marketStudent);
		List<Integer> msStatus = new ArrayList<Integer>();
		msStatus.add(0);
		msStatus.add(1);
		msStatus.add(2);
		msStatus.add(3);
		msStatus.add(4);
		msStatus.add(5);
		map.put("msStatus",msStatus);
		return marketStudentMapper.selectBymsStatusMap(map);
	}
	
	//预定学生管理默认只有预定报名，正式报名
	public List<MarketStudent> selectPredStudentDefault(MarketStudent marketStudent) {
		Map map = new HashMap();
		map.put("marketStudent", marketStudent);
		List<Integer> msStatus = new ArrayList<Integer>();
		msStatus.add(1);
		msStatus.add(2);
		map.put("msStatus",msStatus);
		return marketStudentMapper.selectBymsStatusMap(map);
	}

	@Override
	public List<MarketStudent> selectPredStudentAllRand() {
		return marketStudentMapper.selectPredStudentAllRand();
	}

	public List<MarketStudent> selectTestStudentAll() {
		return marketStudentMapper.selectTestStudentAll();
	}

	@Override
	public int countByclsId(Integer classid) {
		return marketStudentMapper.countByclsId(classid);
	}

	public MarketStudentCount countByYearStatus(Integer msStatus, Integer year) {
		Map map =new HashMap();
		map.put("msStatus", msStatus);
		map.put("year", year);
		return marketStudentMapper.countByYearStatus(map);
	}

	@Override
	public MarketStudentCount countIntenByYear(Integer year) {
		return marketStudentMapper.countIntenByYear(year);
	}

	public MarketStudentCount countTestByYear(Integer year) {
		return marketStudentMapper.countTestByYear(year);
	}

	@Override
	public int reportIntenYearByYear(Integer year) {
		return marketStudentMapper.reportIntenYearByYear(year);
	}

	@Override
	public int reportYearByYearStatus(Integer msStatus, Integer year) {
		Map map =new HashMap();
		map.put("msStatus", msStatus);
		map.put("year", year);
		return marketStudentMapper.reportYearByYearStatus(map);
	}

	@Override
	public int reportTestYearByYear(Integer year) {
		return marketStudentMapper.reportTestYearByYear(year);
	}
}
