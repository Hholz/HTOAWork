package com.ht.controller.student;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.AdjustClass;
import com.ht.popj.student.Student;
import com.ht.service.student.AdjustClassService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
/*
 * ��ʦ����ְ�����
 */

@Controller
@RequestMapping("/student/deladjustCls")
public class DelAdjustClassController {

	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StudentService studentService;
	@Autowired
	AdjustClassService adjustClassService;
	
	@RequestMapping("/page")
	@SystemControllerLog(description = "���봦��ѧ���ְ�ҳ��")  
	public String adjustCls(Model model){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}else{
			return "noEmp";
		}
		List<AdjustClass> adjList = new ArrayList<AdjustClass>();
		adjList = adjustClassService.selectbyTeacId(emp.getId());
		
		List<AdjustClass> toDelList = new ArrayList<AdjustClass>();
		toDelList = adjustClassService.selectbyTeacIdNodel(emp.getId());
		
		List<AdjustClass> toDelList2 = new ArrayList<AdjustClass>();
		toDelList2 = adjustClassService.selectbyTeacIdNodel2(emp.getId());
		
		model.addAttribute("adjList", adjList);
		model.addAttribute("toDelList", toDelList);
		model.addAttribute("toDelList2", toDelList2);
		return "student/tecGetAjc";
	}
	/*
	 * ͬ��ѧ���������İ�
	 */
	@RequestMapping("/succ")
	@SystemControllerLog(description = "ͬ������༶")  
	public String succ(int id){
		AdjustClass ac = new AdjustClass();
		ac.setId(id);
		ac.setAcStatus(1);
		adjustClassService.updateByPJ(ac);
		return "redirect:page";
	}
	/*
	 * ��ͬ��ѧ���������İ�
	 */
	@RequestMapping("/fail")
	@SystemControllerLog(description = "�ܾ������༶")  
	public String fail(int id){
		AdjustClass ac = new AdjustClass();
		ac.setId(id);
		ac.setAcStatus(2);
		adjustClassService.updateByPJ(ac);
		return "redirect:page";
	}
	/*
	 * ͬ����˵��Ұ����
	 */
	@RequestMapping("/succ2")
	@SystemControllerLog(description = "ͬ������༶")  
	public String succ2(int id){
		AdjustClass ac = new AdjustClass();
		ac.setId(id);
		ac.setAcStatus(3);
		adjustClassService.updateByPJ(ac);
		
		AdjustClass adjustClass = new AdjustClass();
		adjustClass = adjustClassService.selectById(id);
		Student student = new Student();
		student.setId(adjustClass.getStuId());
		student.setClassid(adjustClass.getToclsid());
		//������ʦ��ͬ���˾͸����༶
		studentService.updateStuById(student);
		return "redirect:page";
	}/*
	 * ��ͬ����˵��Ұ����
	 */
	@RequestMapping("/fail2")
	@SystemControllerLog(description = "�ܾ������༶")  
	public String fail2(int id){
		AdjustClass ac = new AdjustClass();
		ac.setId(id);
		ac.setAcStatus(4);
		adjustClassService.updateByPJ(ac);
		return "redirect:page";
	}
}
