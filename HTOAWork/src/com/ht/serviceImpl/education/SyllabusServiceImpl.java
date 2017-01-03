package com.ht.serviceImpl.education;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ht.mapper.education.EduSyllabusMapper;
import com.ht.popj.education.EduCourseQuery;
import com.ht.popj.education.EduSyllabus;
import com.ht.service.education.SyllabusService;

@Repository
public class SyllabusServiceImpl implements SyllabusService{

	@Autowired
	EduSyllabusMapper mapper;
	@Override
	public int deleteSyllabus(Integer id) {
		return mapper.deleteSyllabus(id);
	}

	@Override
	public int addSyllabus(EduSyllabus record) {
		return mapper.addSyllabus(record);
	}

	@Override
	public EduSyllabus getSyllabus(Integer id) {
		return mapper.getSyllabus(id);
	}

	@Override
	public int updateSyllabus(EduSyllabus record) {
		return mapper.updateSyllabus(record);
	}

	@Override
	public List<EduSyllabus> getSomeSyllabus(EduSyllabus record) {
		return mapper.getSomeSyllabus(record);
	}

	@Override
	public List<EduSyllabus> getAllSyllabus() {
		return mapper.getAllSyllabus();
	}

	@Override
	public List<EduSyllabus> getPlanSyllabus(EduSyllabus record) {
		return mapper.getPlanSyllabus(record);
	}

	@Override
	public List<EduCourseQuery> getCourse(Map map) {
		// TODO Auto-generated method stub
		/*Map map = new HashMap();
		map.put("empId", empId);*/
		return mapper.getCourse(map);
	}

}
