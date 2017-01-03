package com.ht.service.dailyWork;

import java.io.OutputStream;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.ht.popj.dailyWork.Attendances;
import com.ht.popj.student.StudentReward;

public interface AttendanceService {

	public int insertSelective(Attendances record);
	
	public List<Attendances> attendanceselect(Attendances record);
	
	public int updateByPrimaryKeySelective(Attendances record);
	
	public boolean exportExcel(List<Attendances> list, OutputStream out,String sheetName) ;
	
	public JSONObject importStudAtten(MultipartFile file);
	
}
