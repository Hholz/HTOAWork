package com.ht.mapper.education;

import com.ht.popj.education.EduSyllabusRecord;

public interface EduSyllabusRecordMapper {
    int deleteSyllabusRecord(Integer id);

    int addSyllabusRecord(EduSyllabusRecord record);

    EduSyllabusRecord getSyllabusRecord(Integer id);
    
    EduSyllabusRecord getSyllabusRecordBySyllabusRecord(EduSyllabusRecord record);
    
    int updateSyllabusRecord(EduSyllabusRecord record);
}