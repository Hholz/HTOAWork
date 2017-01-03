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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentHourse;
import com.ht.popj.student.StudentScore;
import com.ht.popj.sysSet.FlowModelType;
import com.ht.service.student.StudentFeedBackService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.service.sysSet.FlowModelTypeService;
import com.ht.util.ResultMessage;
import com.ht.popj.student.StudentFeedBack;

@Controller
@RequestMapping("/student/studentFeedBack")
public class StudentFeedBackController {

	@Autowired
	StudentService studentService;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	FlowModelTypeService flowModelTypeService;
	@Autowired
	StudentFeedBackService studentFeedBackService;
	@RequestMapping("/studentFeedBack")
	@SystemControllerLog(description = "进入学生退费页面")
	public String StudentFeedBack(Model model) throws Exception{
		List<StudentClass> studentclasslist = studentInfoService.selectallstduentclass();
		List<Student>  studentlist = studentService.selectStudentAll();
		List<FlowModelType> flowModeTypelist = flowModelTypeService.findList(null);
		
		model.addAttribute("studentclasslist", studentclasslist);
		model.addAttribute("studentlist", studentlist);
		model.addAttribute("flowModeTypelist", flowModeTypelist);
		
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null !=userInfo.getEmp()){
			emp = userInfo.getEmp();
			if(emp.getEmpname().equals("老陈")){
				return "/student/studentFeedBackAgree";
			}
		}
			return "/student/studentFeedBack";
	}
	
	@RequestMapping("/FeedBackJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "学生退费controller里的list表")
	public @ResponseBody ResultMessage JsonList(int limit, int offset,StudentFeedBack feed){
		ResultMessage rm = new ResultMessage();
		List<StudentFeedBack> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(null!=feed){
			sList = studentFeedBackService.slectDynamic(feed);
		}
		 // 取分页信息
        PageInfo<StudentFeedBack> pageInfo = new PageInfo<StudentFeedBack>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/findstudent")
	public @ResponseBody ResultMessage findemp(Model model,Student s) {
		ResultMessage rm = new ResultMessage();
		List<Student> emplist =  studentService.selectByDynamic(s);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	@RequestMapping("/addfeedback")
	public @ResponseBody int addScore(StudentFeedBack score) throws Exception{ 
		//生成随机密码
		if(null!=score){
			int count = studentFeedBackService.insert(score);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStatuslD(StudentFeedBack feedback) throws Exception{  
		if(null!=feedback){
			int count = studentFeedBackService.updateByPrimaryKeySelective(feedback);
			return count;
		}
		return 0;
	}
}
