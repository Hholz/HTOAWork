package com.ht.mapper.education;

import java.util.List;

import com.ht.popj.education.EduFeedbackDetail;

public interface EduFeedbackDetailMapper {
	int deleteFeedbackDetail(Integer id);

	int addFeedbackDetail(EduFeedbackDetail record);

	EduFeedbackDetail getFeedbackDetail(Integer id);

	int updateFeedbackDetail(EduFeedbackDetail record);

	List<EduFeedbackDetail> getAllFeedbackDetail(EduFeedbackDetail record);

	List<EduFeedbackDetail> getSomeFeedBackDetail(EduFeedbackDetail record);

	List<Integer> getSumByFeedBackId(Integer id);
}