package com.ht.service.dailyWork;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.ht.popj.dailyWork.Attendance_tal;

public interface Attendance_talService {
	/*
	 * 查询考勤模板表
	 * */
	public List<Attendance_tal> attendancelist(Attendance_tal attendance_tal)throws Exception;
	/*
	 * 按月生成模板数据
	 * */
	public void addattendance(Attendance_tal attendance_tal)throws Exception;
	/*
	 * 考勤数据导入
	 * */
	public JSONObject importStudAtten(MultipartFile file);
	/*
	 * 考勤数据修改
	 * */
	public void insertInreStud(Attendance_tal attendance_tal)throws Exception;
	/*
	 * 查询考勤模板表数据-条件查询
	 * */
	public Attendance_tal findAttendance(Attendance_tal attendance_tal)throws Exception;
	/*
	 * 删除函数-默认删除上个月的所有数据
	 * */
	public void deleteAttendance()throws Exception;
	/*
	 * 查询考勤模板表数据-返回多条数据
	 * */
	public List<Attendance_tal> fAttendance(Attendance_tal attendance_tal)throws Exception;
	/*
	 * 正常数据
	 * */
	public List<Attendance_tal> normalAttendance()throws Exception;
	/*
	 * 异常数据
	 * */
	public List<Attendance_tal> abnormalAttendance()throws Exception;
	/*
	 * 旷工数据
	 * */
	public List<Attendance_tal> absenteeism()throws Exception;
	/*
	 * 迟到数据
	 * */
	public List<Attendance_tal> lateAttendance()throws Exception;
	/*
	 * 
	 * */
	public List<Attendance_tal> findidAttendance(int tal)throws Exception;
	
	List<Attendance_tal> selectCount();
}
