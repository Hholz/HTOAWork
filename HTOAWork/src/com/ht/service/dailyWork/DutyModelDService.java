package com.ht.service.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.DutyMaxTemp;
import com.ht.popj.dailyWork.Dutymodeldetail;


public interface DutyModelDService {

	int deleteById(Integer id);

    int insertByPJ(Dutymodeldetail dmd);

    Dutymodeldetail selectById(Integer id);

    int updateByPJ(Dutymodeldetail dmd);
    
    List<Dutymodeldetail> selectAll();
    
    List<Dutymodeldetail> selectByDutymodelId(Integer modelid);//ͨ��ģ��id��

    //��ѯ��ģ�壬ĳ��ֵ�������ͣ�����ж��ٸ�
    List<DutyMaxTemp> selectDutyMaxBymodelId(Integer modelid);
    
    //��ѯ��ģ�壬���ܼ�������List<String>
    List<String> selectWeksBymodelId(Integer modelid);

	List<Dutymodeldetail> selectByPJ(Dutymodeldetail temp);
}
