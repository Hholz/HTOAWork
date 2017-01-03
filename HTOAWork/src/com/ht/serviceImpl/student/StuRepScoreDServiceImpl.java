package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuRepScoreDMapper;
import com.ht.popj.student.StuRepScoreD;
import com.ht.service.student.StuRepScoreDService;

/**
 * 学生答辩得分明细表
 * @author HYP
 *
 */
public class StuRepScoreDServiceImpl implements StuRepScoreDService{

	@Autowired
	StuRepScoreDMapper srsdMapper;

	@Override
	public int deleteById(Integer id) {
		return srsdMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insertByPJ(StuRepScoreD srsd) {
		return srsdMapper.insertSelective(srsd);
	}

	@Override
	public StuRepScoreD selectById(Integer id) {
		return srsdMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPJ(StuRepScoreD srsd) {
		return srsdMapper.updateByPrimaryKeySelective(srsd);
	}

	@Override
	public int delByUpdate(Integer id) {
		return srsdMapper.delByUpdate(id);
	}

	@Override
	public List<StuRepScoreD> selectAll() {
		return srsdMapper.selectAll();
	}

	@Override
	public List<StuRepScoreD> selectBysrsId(Integer id) {
		return srsdMapper.selectBysrsId(id);
	}

	@Override
	public int countBysrmdId(Integer srmdId) {
		return srsdMapper.countBysrmdId(srmdId);
	}
	
}
