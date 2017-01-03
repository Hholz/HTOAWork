package com.ht.service.education;

import java.util.List;
import java.util.Map;

import com.ht.popj.education.EduCourseQuery;
import com.ht.popj.education.EduSyllabus;

public interface SyllabusService {

	int deleteSyllabus(Integer id);

	int addSyllabus(EduSyllabus record);

	EduSyllabus getSyllabus(Integer id);

	int updateSyllabus(EduSyllabus record);

	List<EduSyllabus> getSomeSyllabus(EduSyllabus record);

	List<EduSyllabus> getAllSyllabus();
	
	List<EduSyllabus> getPlanSyllabus(EduSyllabus record);
	
	List<EduCourseQuery> getCourse(Map map);
}
