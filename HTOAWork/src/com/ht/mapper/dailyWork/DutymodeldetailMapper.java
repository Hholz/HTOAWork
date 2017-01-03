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

	List<Dutymodeldetail> selectByDutymodelId(Integer modelid);//ͨ��ģ��id��ģ����ϸ

	//��ģ�� �� ��ѯһ����,ĳֵ������ �������ж��ٸ�
	List<DutyMaxTemp> selectDutyMaxBymodelId(Integer modelid);

	//��ѯ��ģ�壬���ܼ�������List<String>
	List<String> selectWeksBymodelId(Integer modelid);

	List<Dutymodeldetail> selectByPJ(Dutymodeldetail temp);
}