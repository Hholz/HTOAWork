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
    
    List<Dutymodeldetail> selectByDutymodelId(Integer modelid);//通过模板id查

    //查询该模板，某个值班人类型，最大有多少个
    List<DutyMaxTemp> selectDutyMaxBymodelId(Integer modelid);
    
    //查询该模板，有周几，返回List<String>
    List<String> selectWeksBymodelId(Integer modelid);

	List<Dutymodeldetail> selectByPJ(Dutymodeldetail temp);
}
