package com.ht.service.student;

import java.util.List;
import java.util.Map;

import com.ht.popj.student.AdjustClass;

public interface AdjustClassService {

	int deleteById(Integer id);

    int insertByPJ(AdjustClass record);

    AdjustClass selectById(Integer id);

    int updateByPJ(AdjustClass record);

    List<AdjustClass> selectbyStuId(Integer stuId);
    //map里放stuId，查询该班主任所有学生的请假单（已处理）
    List<AdjustClass> selectbyTeacId(String empId);
  //map里放stuId，查询该班主任所有学生的请假单（未处理）
    List<AdjustClass> selectbyTeacIdNodel(String empId);
    
    //map里放stuId，查询该班主任所有学生的请假单（未处理）
    List<AdjustClass> selectbyTeacIdNodel2(String empId);
}
