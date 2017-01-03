package com.ht.controller.student;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StuHoliday;
import com.ht.popj.student.Student;
import com.ht.service.student.StuHolidayService;
import com.ht.util.RandomGet;


@Controller
@RequestMapping("/stuHoliday")
public class StuHolidayController {

	@Autowired
	StuHolidayService stuHolidayService;
	@RequestMapping("/page")
	public String stuList(Model model){
		List<StuHoliday> holList = new ArrayList<StuHoliday>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null!=userInfo.getStudent()){
			student = userInfo.getStudent();
		}else{
			return "noStudent";
		}
		holList = stuHolidayService.selectByStuId(student.getId());
		model.addAttribute("holList", holList);
		return "student/stuHoliday";
	}
	@RequestMapping("/add")
	public String add(StuHoliday holiday ,Model model){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null!=userInfo.getStudent()){
			student = userInfo.getStudent();
			holiday.setStuId(student.getId());
		}
		//…Ë÷√±‡∫≈
		String date = new Date().toLocaleString().substring(0, 10);
		date = date.replace("-", "");
		holiday.setSerialnum("XSQJ"+date+RandomGet.getSix());
		holiday.setStatus(0);//0¥˝¥¶¿Ì
		if(holiday!=null){
			stuHolidayService.insertByPJ(holiday);
		}
		return "redirect:page";
	}
}
