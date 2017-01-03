package com.ht.mapper.market;

import java.util.List;

import com.ht.popj.market.TrackStudent;

public interface TrackStudentMapper {
    //跟踪学生列表
	List<TrackStudent> findTrackStudentList1(TrackStudent trackstu);
	List<TrackStudent> findTrackStudentList2();
	//新增跟踪学生
	int addTrackStudent(TrackStudent trackstu);
	//修改跟踪学生
	int updateTrackStudent(TrackStudent trackstu);
	//删除跟踪学生
	int deleteTrackStudentById(Integer id);
	
	TrackStudent selectById(Integer id);
}