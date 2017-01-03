package com.ht.service.education;

import java.util.List;

import com.ht.popj.education.EduFeedbackDetail;

public interface FeedBackDetailService {
	int deleteFeedbackDetail(Integer id);

	int addFeedbackDetail(EduFeedbackDetail record);

	EduFeedbackDetail getFeedbackDetail(Integer id);

	int updateFeedbackDetail(EduFeedbackDetail record);

	List<EduFeedbackDetail> getAllFeedbackDetail(EduFeedbackDetail record);

	List<EduFeedbackDetail> getSomeFeedBackDetail(EduFeedbackDetail record);
	
	List<Integer> getSumByFeedBackId(Integer id);
}
