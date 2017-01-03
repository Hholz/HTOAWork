package com.ht.mapper.education;


import java.util.List;

import com.ht.popj.education.EduSeminarDatail;

public interface EduSeminarDatailMapper {
    int deleteSeminarDatail(Integer id);

    int addSeminarDatail(EduSeminarDatail record);

    EduSeminarDatail getSeminarDatail(Integer id);

    int updateSeminarDatail(EduSeminarDatail record);

    List<EduSeminarDatail> getSomeSiminarDetail(EduSeminarDatail record);
}