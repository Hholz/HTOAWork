package com.ht.service.student;

import java.io.OutputStream;
import java.util.List;



import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

import com.ht.popj.student.StudentReward;
import com.ht.popj.student.StudentSayface;

public interface StudentrewardService {
	
	public List<StudentReward> studentrewardsel(StudentReward record);
	
	public int insertSelective(StudentReward record);
	
	public int updateByPrimaryKeySelective(StudentReward record);
	
	public boolean exportExcel(List<StudentReward> list, OutputStream out,String sheetName) ;
	
	public JSONObject importStudReward(MultipartFile file);
	
	public List<StudentReward> selectByStuId(Integer stuId);
}
