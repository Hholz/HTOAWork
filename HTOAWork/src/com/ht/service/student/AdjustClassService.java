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
    //map���stuId����ѯ�ð���������ѧ������ٵ����Ѵ���
    List<AdjustClass> selectbyTeacId(String empId);
  //map���stuId����ѯ�ð���������ѧ������ٵ���δ����
    List<AdjustClass> selectbyTeacIdNodel(String empId);
    
    //map���stuId����ѯ�ð���������ѧ������ٵ���δ����
    List<AdjustClass> selectbyTeacIdNodel2(String empId);
}
