package com.ht.service.student;

import java.util.List;

import com.ht.popj.student.StuOptionSort;

public interface StuOptionSortService {
	 StuOptionSort selectByPrimaryKey(Integer id);
	 List<StuOptionSort> selectDynamic(StuOptionSort record);
}
