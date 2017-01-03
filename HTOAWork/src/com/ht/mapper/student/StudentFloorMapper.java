package com.ht.mapper.student;

import java.util.List;

import com.ht.popj.student.StudentFloor;

public interface StudentFloorMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(StudentFloor record);

    int insertSelective(StudentFloor record);

    StudentFloor selectByPrimaryKey(Integer id);
    //�޸�¥��
    int updateByPrimaryKeySelective(StudentFloor record);

    int updateByPrimaryKey(StudentFloor record);
    
    List<StudentFloor> selectStudentFloorAll();
    //��̬��ѯ
    List<StudentFloor> selectByDynamic(StudentFloor studentFloor);
    
    //�޸�¥��״̬
    int updateStatusPrimaryKey(Integer id);
}