package com.ht.serviceImpl.education;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduSyllabusRecordMapper;
import com.ht.popj.education.EduSyllabusRecord;
import com.ht.service.education.SyllabusRecordService;

public class SyllabusRecordServiceImpl implements SyllabusRecordService {

	@Autowired
	EduSyllabusRecordMapper mapper;
	@Override
	public int deleteSyllabusRecord(Integer id) {
		return mapper.deleteSyllabusRecord(id);
	}

	@Override
	public int addSyllabusRecord(EduSyllabusRecord record) {
		return mapper.addSyllabusRecord(record);
	}

	@Override
	public EduSyllabusRecord getSyllabusRecord(Integer id) {
		return mapper.getSyllabusRecord(id);
	}


	@Override
	public int updateSyllabusRecord(EduSyllabusRecord record) {
		return mapper.updateSyllabusRecord(record);
	}

	@Override
	public EduSyllabusRecord getSyllabusRecordBySyllabusRecord(EduSyllabusRecord record) {
		return mapper.getSyllabusRecordBySyllabusRecord(record);
	}

}
