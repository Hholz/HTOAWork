package com.ht.serviceImpl.education;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.education.EduFeedbackMapper;
import com.ht.popj.education.EduFeedback;
import com.ht.service.education.FeedBackService;

public class FeedBackServiceImpl implements FeedBackService {

	@Autowired
	EduFeedbackMapper mapper;
	@Override
	public int deleteFeedback(Integer id) {
		return mapper.deleteFeedback(id);
	}

	@Override
	public int addFeedback(EduFeedback record) {
		return mapper.addFeedback(record);
	}

	@Override
	public EduFeedback getFeedback(Integer id) {
		return mapper.getFeedback(id);
	}

	@Override
	public int updateFeedback(EduFeedback record) {
		return mapper.updateFeedback(record);
	}

	@Override
	public List<EduFeedback> getAllFeedback(EduFeedback record) {
		return mapper.getAllFeedback(record);
	}

	@Override
	public List<EduFeedback> getSomeFeedBack(EduFeedback record) {
		return mapper.getSomeFeedBack(record);
	}

	@Override
	public List<EduFeedback> getNoStudentFeedBack(EduFeedback record) {
		return mapper.getNoStudentFeedBack(record);
	}

}
