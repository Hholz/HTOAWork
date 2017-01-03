package com.ht.serviceImpl.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.student.StuRepSetMapper;
import com.ht.popj.student.StuRepSet;
import com.ht.service.student.StuRepSetService;

public class StuRepSetServiceImpl implements StuRepSetService{
	@Autowired
	StuRepSetMapper sturepsetMapper;

	@Override
	public List<StuRepSet> selectStuRepSet(StuRepSet sturepset) {
		return sturepsetMapper.selectStuRepSet(sturepset);
	}

	@Override
	public int insertStuRepSet(StuRepSet sturepset) {
		return sturepsetMapper.insertStuRepSet(sturepset);
	}

	@Override
	public int updateStuRepSet(StuRepSet sturepset) {
		return sturepsetMapper.updateStuRepSet(sturepset);
	}

	@Override
	public int deleteStuRepSet(Integer id) {
		return sturepsetMapper.deleteStuRepSet(id);
	}

}
