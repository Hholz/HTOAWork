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

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.AdjustClass;
import com.ht.popj.student.Student;
import com.ht.service.student.AdjustClassService;
/*
 * 学生填写分班申请
 */
@Controller
@RequestMapping("/student/adjustCls")
public class AdjustClassController {

	@Autowired
	AdjustClassService adjustClassService;
	@RequestMapping("/page")
	@SystemControllerLog(description = "进入学生分班页面")  
	public String adjustCls(Model model){
		//通过学生id查询他的所有请假详情
		List<AdjustClass> ajcList = new ArrayList<AdjustClass>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null!=userInfo.getStudent()){
			student = userInfo.getStudent();
			ajcList = adjustClassService.selectbyStuId(student.getId());
		}else{
			return "noStudent";
		}
		model.addAttribute("ajcList", ajcList);
		return "student/adjustCls";
	}
	@RequestMapping("/add")
	@SystemControllerLog(description = "学生分班单新增")  
	public String add(Model model,AdjustClass adjustCls){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null!=userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		adjustCls.setClsid(student.getClassid());
		adjustCls.setStuId(student.getId());
		adjustCls.setCreateTime(new Date().toLocaleString());
		adjustCls.setStuatus(0);//设置状态为0，表未处理
		adjustClassService.insertByPJ(adjustCls);
		return "redirect:page";
	}
}
