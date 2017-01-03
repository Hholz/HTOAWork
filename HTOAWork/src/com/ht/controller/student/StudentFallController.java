package com.ht.controller.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.StudentFall;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/fall")
public class StudentFallController {

	@Autowired
	StudentInfoService studentInfoService;
	//������ҳ��
	@RequestMapping("/page")
	public String studentListfall(){
		return "student/Studentfall";
	}
	
	@RequestMapping("/fallListJson")
	@SystemControllerLog(description = "ѧ�����controller���list��")
	public @ResponseBody ResultMessage listJsonfall(int limit, int offset,Model model,StudentFall student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentFall> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = studentInfoService.selectByDynamic(student);
		}else{
			sList = studentInfoService.selectStudentFall();
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<StudentFall> pageInfo = new PageInfo<StudentFall>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//�������
	@RequestMapping("/add")
	public @ResponseBody int addStudentfall(Model model,StudentFall student) throws Exception{ 
		if(null!=student){
			if(null==student.getLevel()||student.getLevel().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			studentInfoService.insertSelective(student);
			return 1;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//�޸Ľ��
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentfall(Model model,StudentFall student){  
		if(null!=student){
			if(null==student.getLevel()||student.getLevel().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}			
			studentInfoService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
	
	//ɾ�����
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentfall(Model model,StudentFall student){  
		if(null!=student){
			if(null==student.getId()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}			
			studentInfoService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
}
