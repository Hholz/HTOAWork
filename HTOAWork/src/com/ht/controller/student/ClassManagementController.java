package com.ht.controller.student;
/*
 * �༶����
 */

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduMajor;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.popj.sysSet.Residence;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.MajorService;
import com.ht.service.student.ClassManagementService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.sysSet.ResidenceService;
import com.ht.service.sysSet.StuStatusService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

import common.Logger;

@Controller
@RequestMapping("/student/myclass")
public class ClassManagementController {
	
	Logger logger = Logger.getLogger(ClassManagementController.class);

	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	EmpService empservice;
	//רҵ��service
	@Autowired
	MajorService majorService;
	@Autowired
	ResidenceService residenceService;
	@Autowired
	StuStatusService stuStatusService;
	
	//�༶����service��
	@Autowired
	ClassManagementService classManagementService;
	//����༶����ҳ�棨ֻ���Լ��̵İ༶��
	@RequestMapping("/page")
	public String page(Model model,StudentClass sc,Emp emp) throws Exception{
		List<StudentFall> sList = studentInfoService.selectStudentFall();
		model.addAttribute("sList", sList);
		return "student/ClassManagement";
	}
	
	@RequestMapping("/classListJson")
	@SystemControllerLog(description = "�༶����controller���list��")
	public @ResponseBody ResultMessage classlistJson(int limit, int offset,Model model,StudentClass studentClass) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentClass> sList = new ArrayList<>();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);
		//��Ӧ�����˵Ĺ���İ༶
		if(null != UserInfoUtil.getEmp()){
			studentClass.setClteacherId(UserInfoUtil.getEmp().getId());
			studentClass.setLeaderId(UserInfoUtil.getEmp().getId());
			sList = studentInfoService.selectNormalCls(studentClass);
		}
        PageInfo<StudentClass> pageInfo = new PageInfo<StudentClass>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//�ְ����
	@RequestMapping("/stuclassallot")
	public @ResponseBody int Studentclassallot(Integer classid,String ids){
		return classManagementService.Studentclassallot(ids,classid);
	}
	
	@RequestMapping("/stuList/{clsId}")
	public String stuList(Model model,@PathVariable("clsId")Integer clsId){
		//��ȡ�༶��Ϣ
		StudentClass cls = studentInfoService.selectById(clsId);
		//��ȡ��������
		List<Residence> resList = new ArrayList<Residence>();
		resList = residenceService.selectAll();
		//��ȡרҵ���
		List<EduMajor> majorList = new ArrayList<EduMajor>();
		majorList = majorService.selectMajorAll();
		//��ȡѧ�����
		List<StuStatus> stustaList = new ArrayList<StuStatus>();
		stustaList = stuStatusService.selectAll();
		
		model.addAttribute("resList", resList);
		model.addAttribute("majorList", majorList);
		model.addAttribute("stustaList", stustaList);
		
		model.addAttribute("cls", cls);//��ǰ�༶��Ϣ
		return "student/clsStudent";
	}
}
