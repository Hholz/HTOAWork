package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.Education;

public interface EducationService {
    int deleteByPrimaryKey(Integer id);

    int insert(Education record);

    Education selectByPrimaryKey(Integer id);

    int updateByPrimaryKey(Education record);

    List<Education> selectEducation(Education record);
}
