package com.ht.serviceImpl.market;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ht.mapper.market.TrackStudentMapper;
import com.ht.popj.market.TrackStudent;
import com.ht.service.market.TrackStudentService;

public class TrackStudentServiceImpl implements TrackStudentService{
	@Autowired
	private TrackStudentMapper trackstuMapper;

	@Override
	public List<TrackStudent> findTrackStudentList1(TrackStudent trackstu) {
		return trackstuMapper.findTrackStudentList1(trackstu);
	}

	@Override
	public List<TrackStudent> findTrackStudentList2() {
		return trackstuMapper.findTrackStudentList2();
	}

	@Override
	public int addTrackStudent(TrackStudent trackstu) {
		return trackstuMapper.addTrackStudent(trackstu);
	}

	@Override
	public int updateTrackStudent(TrackStudent trackstu) {
		return trackstuMapper.updateTrackStudent(trackstu);
	}

	@Override
	public int deleteTrackStudentById(Integer id) {
		return trackstuMapper.deleteTrackStudentById(id);
	}

	@Override
	public TrackStudent selectById(Integer id) {
		return trackstuMapper.selectById(id);
	}

}
