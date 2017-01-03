package com.ht.service.dailyWork;

import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.ht.popj.dailyWork.Attendance_tal;

public interface Attendance_talService {
	/*
	 * ��ѯ����ģ���
	 * */
	public List<Attendance_tal> attendancelist(Attendance_tal attendance_tal)throws Exception;
	/*
	 * ��������ģ������
	 * */
	public void addattendance(Attendance_tal attendance_tal)throws Exception;
	/*
	 * �������ݵ���
	 * */
	public JSONObject importStudAtten(MultipartFile file);
	/*
	 * ���������޸�
	 * */
	public void insertInreStud(Attendance_tal attendance_tal)throws Exception;
	/*
	 * ��ѯ����ģ�������-������ѯ
	 * */
	public Attendance_tal findAttendance(Attendance_tal attendance_tal)throws Exception;
	/*
	 * ɾ������-Ĭ��ɾ���ϸ��µ���������
	 * */
	public void deleteAttendance()throws Exception;
	/*
	 * ��ѯ����ģ�������-���ض�������
	 * */
	public List<Attendance_tal> fAttendance(Attendance_tal attendance_tal)throws Exception;
	/*
	 * ��������
	 * */
	public List<Attendance_tal> normalAttendance()throws Exception;
	/*
	 * �쳣����
	 * */
	public List<Attendance_tal> abnormalAttendance()throws Exception;
	/*
	 * ��������
	 * */
	public List<Attendance_tal> absenteeism()throws Exception;
	/*
	 * �ٵ�����
	 * */
	public List<Attendance_tal> lateAttendance()throws Exception;
	/*
	 * 
	 * */
	public List<Attendance_tal> findidAttendance(int tal)throws Exception;
	
	List<Attendance_tal> selectCount();
}
