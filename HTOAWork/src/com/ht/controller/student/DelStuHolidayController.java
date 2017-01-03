package com.ht.controller.student;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StuHoliday;
import com.ht.service.student.StuHolidayService;

/*
 * 老师处理学生请假申请
 */
@Controller
@RequestMapping("/student/delStuHoliday")
public class DelStuHolidayController {

	@Autowired
	StuHolidayService stuHolidayService;
	@RequestMapping("/page")
	@SystemControllerLog(description = "进入处理学生请假申请页面")
	public String page(Model model){
		//通过员工的id取他班上的所有学生的请假
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}else{
			return "noEmp";
		}
		List<StuHoliday> noList = new ArrayList<StuHoliday>();
		noList = stuHolidayService.selectByEmpIdNodel(emp.getId());
		
		List<StuHoliday> delList = new ArrayList<StuHoliday>();
		delList = stuHolidayService.selectByEmpId(emp.getId());
		
		model.addAttribute("noList", noList);
		model.addAttribute("delList", delList);
		return "student/delStuHoliday";
	}
	@RequestMapping("/succ/{id}")
	public String succ(@PathVariable("id")Integer id){
		StuHoliday stuHoliday = new StuHoliday();
		stuHoliday.setId(id);
		stuHoliday.setStatus(1);
		stuHolidayService.updateByPJ(stuHoliday);
		return "redirect:../page";
	}
	@RequestMapping("/fail/{id}")
	public String fail(@PathVariable("id")Integer id){
		StuHoliday stuHoliday = new StuHoliday();
		stuHoliday.setId(id);
		stuHoliday.setStatus(2);
		stuHolidayService.updateByPJ(stuHoliday);
		return "redirect:../page";
	}
		
}
