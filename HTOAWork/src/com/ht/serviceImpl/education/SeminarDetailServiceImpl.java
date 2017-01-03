package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.education.EduSeminarDatailMapper;
import com.ht.popj.education.EduSeminarDatail;
import com.ht.service.education.SeminarDatailService;
@Repository
public class SeminarDetailServiceImpl implements SeminarDatailService {

	@Autowired
	EduSeminarDatailMapper mapper;
	@Override
	public int deleteSeminarDatail(Integer id) {
		return mapper.deleteSeminarDatail(id);
	}

	@Override
	public int addSeminarDatail(EduSeminarDatail record) {
		return mapper.addSeminarDatail(record);
	}

	@Override
	public EduSeminarDatail getSeminarDatail(Integer id) {
		return mapper.getSeminarDatail(id);
	}

	@Override
	public int updateSeminarDatail(EduSeminarDatail record) {
		return mapper.updateSeminarDatail(record);
	}

	@Override
	public List<EduSeminarDatail> getSomeSiminarDetail(EduSeminarDatail record) {
		return mapper.getSomeSiminarDetail(record);
	}

}
