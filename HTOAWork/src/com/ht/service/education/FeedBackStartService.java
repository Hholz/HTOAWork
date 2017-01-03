package com.ht.service.education;

import java.util.List;

import com.ht.popj.education.EduFeedbackStart;

public interface FeedBackStartService {

	int addFeedBackStart(EduFeedbackStart record);

	int updateFeedBackStart(EduFeedbackStart record);

	int deleteFeedBackStart(int id);

	EduFeedbackStart getFeedBackStart(int id);

	List<EduFeedbackStart> getAllFeedBackStart();

	List<EduFeedbackStart> getSomeFeedBackStart(EduFeedbackStart record);
}
