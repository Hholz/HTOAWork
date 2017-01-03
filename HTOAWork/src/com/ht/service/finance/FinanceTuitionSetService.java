package com.ht.service.finance;

import java.util.List;

import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceTuitionset;

public interface FinanceTuitionSetService {

	 int insert(FinanceTuitionset record);
	 int deleteByPrimaryKey(Integer id);
	 int insertSelective(FinanceTuitionset record);
	 List<FinanceTuitionset> selectAll();
	 List<FinanceTuitionset> selectDinamic(FinanceTuitionset stu);
	 List<EduMajor> selectAllMajor();
	 int updateByPrimaryKeySelective(FinanceTuitionset record);
	 List<EduTerm> selectAllterm();
	 int updateByPrimaryKey(FinanceTuitionset record);
}
