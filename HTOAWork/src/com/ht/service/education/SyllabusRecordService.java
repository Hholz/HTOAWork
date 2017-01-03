package com.ht.service.education;

import com.ht.popj.education.EduSyllabusRecord;

public interface SyllabusRecordService {

	int deleteSyllabusRecord(Integer id);

    int addSyllabusRecord(EduSyllabusRecord record);

    EduSyllabusRecord getSyllabusRecord(Integer id);
    
    EduSyllabusRecord getSyllabusRecordBySyllabusRecord(EduSyllabusRecord record);
    
    int updateSyllabusRecord(EduSyllabusRecord record);
}
