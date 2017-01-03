package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.education.EduSeminarMapper;
import com.ht.popj.education.EduSeminar;
import com.ht.service.education.SeminarService;
@Repository
public class SerminarServiceImpl implements SeminarService {

	@Autowired
	EduSeminarMapper mapper;
	@Override
	public int deleteSeminar(Integer id) {
		return mapper.deleteSeminar(id);
	}

	@Override
	public int addSeminar(EduSeminar record) {
		return mapper.addSeminar(record);
	}

	@Override
	public EduSeminar getSeminar(Integer id) {
		return mapper.getSeminar(id);
	}

	@Override
	public int updateSeminar(EduSeminar record) {
		return mapper.updateSeminar(record);
	}

	@Override
	public List<EduSeminar> getAllSeminar() {
		return mapper.getAllSeminar();
	}

	@Override
	public List<EduSeminar> getSomeSeminar(EduSeminar record) {
		return mapper.getSomeSeminar(record);
	}

}
