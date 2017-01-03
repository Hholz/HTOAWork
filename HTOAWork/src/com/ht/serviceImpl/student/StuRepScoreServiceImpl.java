package com.ht.serviceImpl.student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuRepScoreMapper;
import com.ht.popj.student.StuRepScore;
import com.ht.service.student.StuRepScoreService;

/**
 * 学生答辩得分表
 * @author HYP
 *
 */
public class StuRepScoreServiceImpl implements StuRepScoreService{

	@Autowired
	StuRepScoreMapper srsMapper;
	@Override
	public int deleteById(Integer id) {
		return srsMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StuRepScore srs) {
		return srsMapper.insertSelective(srs);
	}

	@Override
	public StuRepScore selectById(Integer id) {
		return srsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StuRepScore srs) {
		return srsMapper.updateByPrimaryKeySelective(srs);
	}

	@Override
	public int delByUpdate(Integer id) {
		return srsMapper.delByUpdate(id);
	}

	@Override
	public List<StuRepScore> selectAll() {
		return srsMapper.selectAll();
	}

	@Override
	public List<StuRepScore> selectByStuId(Integer stuId) {
		return srsMapper.selectByStuId(stuId);
	}

	@Override
	public int countBysrmId(Integer srmId) {
		return srsMapper.countBysrmId(srmId);
	}

	@Override
	public List<StuRepScore> selectByPJ(StuRepScore srs, String mbName, int clsId, String empName) {
		Map map =new HashMap();
		map.put("srs", srs);
		map.put("mbName", mbName);
		map.put("clsId", clsId);
		map.put("empName", empName);
		return srsMapper.selectByPJ(map);
	}

}
