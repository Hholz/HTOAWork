package com.ht.service.market;

import java.util.List;

import com.ht.popj.market.IntentionStudent;

public interface IntentionStudentService {
	//意向学生列表
	List<IntentionStudent> findIntentionStudentList1(IntentionStudent intenstu);
	List<IntentionStudent> findIntentionStudentList2();
	List<IntentionStudent> findIntentionStudentList3();
	//新增意向学生
	int addIntentionStudent(IntentionStudent intenstu);
	//修改意向学生信息
	int updateIntentionStudent(IntentionStudent intenstu);
	//删除意向学生（将状态改为0）
	int deleteIntentionStudentById(Integer id);
	//删除意向学生（彻底删除）
	int deleteIntentionStudent(Integer id);
	//通过id查找信息
	IntentionStudent selectByPrimaryKey(Integer id);
}
