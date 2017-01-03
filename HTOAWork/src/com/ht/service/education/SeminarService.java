package com.ht.service.education;

import java.util.List;

import com.ht.popj.education.EduSeminar;

public interface SeminarService {

	int deleteSeminar(Integer id);

	int addSeminar(EduSeminar record);

	EduSeminar getSeminar(Integer id);

	int updateSeminar(EduSeminar record);

	List<EduSeminar> getAllSeminar();

	List<EduSeminar> getSomeSeminar(EduSeminar record);
}
