package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentReward;

public interface StudentRewardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentReward record);

    int insertSelective(StudentReward record);

    StudentReward selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentReward record);

    int updateByPrimaryKey(StudentReward record);
    
    List<StudentReward> studentrewardsel(StudentReward record);
    
    List<StudentReward> selectByStuId(Integer stuId);
}