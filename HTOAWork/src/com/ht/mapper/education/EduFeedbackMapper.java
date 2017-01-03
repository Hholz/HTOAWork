package com.ht.mapper.education;

import java.util.List;

import com.ht.popj.education.EduFeedback;

public interface EduFeedbackMapper {
	int deleteFeedback(Integer id);

	int addFeedback(EduFeedback record);

	EduFeedback getFeedback(Integer id);

	int updateFeedback(EduFeedback record);

	List<EduFeedback> getAllFeedback(EduFeedback record);

	List<EduFeedback> getSomeFeedBack(EduFeedback record);

	List<EduFeedback> getNoStudentFeedBack(EduFeedback record);
}