package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentLayer;

public interface StudentLayerMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentLayer record);

    int insertSelective(StudentLayer record);

    StudentLayer selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(StudentLayer record);

    int updateByPrimaryKey(StudentLayer record);
    
    int updateLayerStatusPrimaryKey(Integer id);
    //��ѯ����¥����Ϣ
    List<StudentLayer> selectStudentLayerAll();
    List<StudentLayer> selectStudentLayerNameAll();
    
    List<StudentLayer> selectDynamic(StudentLayer record);
}