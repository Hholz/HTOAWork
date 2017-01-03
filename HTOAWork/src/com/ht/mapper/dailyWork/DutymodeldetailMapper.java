package com.ht.mapper.dailyWork;

import java.util.List;

import com.ht.popj.dailyWork.DutyMaxTemp;
import com.ht.popj.dailyWork.Dutymodeldetail;

public interface DutymodeldetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Dutymodeldetail record);

    int insertSelective(Dutymodeldetail record);

    Dutymodeldetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Dutymodeldetail record);

    int updateByPrimaryKey(Dutymodeldetail record);

	List<Dutymodeldetail> selectAll();

	List<Dutymodeldetail> selectByDutymodelId(Integer modelid);//通过模板id查模板明细

	//该模板 ， 查询一周中,某值班类型 最大的是有多少个
	List<DutyMaxTemp> selectDutyMaxBymodelId(Integer modelid);

	//查询该模板，有周几，返回List<String>
	List<String> selectWeksBymodelId(Integer modelid);

	List<Dutymodeldetail> selectByPJ(Dutymodeldetail temp);
}