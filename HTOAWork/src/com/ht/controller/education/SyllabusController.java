package com.ht.controller.education;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduCourseTeacher;
import com.ht.popj.education.EduOutline;
import com.ht.popj.education.EduSyllabus;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.StudentClass;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.CourseService;
import com.ht.service.education.CourseTeacherService;
import com.ht.service.education.OutlineService;
import com.ht.service.education.SyllabusService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/syllabus")
public class SyllabusController {

	@Autowired
	SyllabusService syllabusService;
	@Autowired
	EmpService empservice;
	@Autowired
	OutlineService outlineService;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	CourseTeacherService courseTeacherService;
	@Autowired
	CourseService courseService;
	@RequestMapping("/list")
	@SystemControllerLog(description="进入课程进度计划页面")
	public String syllabusList(Model model){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		model.addAttribute("empId", emp.getId());
		List<Emp> allEmp = empservice.selectEmp(new Emp());
		model.addAttribute("allEmp",allEmp);
		StudentClass educl=new StudentClass();
		educl.setTeacherId(emp.getId());
		List<StudentClass> allClass=studentInfoService.selectStudentclass(educl);//该登录员工所教的课程
		model.addAttribute("allClass",allClass);
		List<EduOutline> allOutline = outlineService.selectByDynamic(new EduOutline());
		model.addAttribute("allOutline",allOutline);
		List<EduCourse> allCourse = new ArrayList<EduCourse>();
		allCourse = courseService.selectCourseAll();
		model.addAttribute("allCourse",allCourse);
		return "/education/syllabus";
	}
	
	// bootstrop table 里的url用来获取Json数据
	@RequestMapping("/syllabuslist")
	@SystemControllerLog(description="返回课程计划进度json数据")
	public @ResponseBody ResultMessage list(int limit, int offset, Model model, EduSyllabus syllabus){
		ResultMessage rm = new ResultMessage();
		List<EduSyllabus> sList = new ArrayList<EduSyllabus>();
		//计算页数
		int pageCount = (int)Math.ceil(offset / (limit*1.0));
		// 该页数是从1开始（当前页数，一页显示的条数）
		PageHelper.startPage(pageCount+1,limit);
		if(syllabus!=null){
			sList = syllabusService.getSomeSyllabus(syllabus);
		}else{
			sList = syllabusService.getAllSyllabus();
		}
		//这是分页信息
		PageInfo<EduSyllabus> pageInfo = new PageInfo<>(sList);
		long total = pageInfo.getTotal();
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	@RequestMapping("/add")
	@SystemControllerLog(description="新增课程计划进度")
	public @ResponseBody int addSyllabus(EduSyllabus syllabus){
		if(syllabus!=null){
			syllabus.setCreateTime(new Date().toLocaleString());
			int count = syllabusService.addSyllabus(syllabus);
			return count;
		}
		return 0;
	}
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description="修改课程计划进度")
	public @ResponseBody int update(EduSyllabus syllabus) {
		if (null != syllabus && syllabus.getId()!=null) {
			syllabus.setUpdateTime(new Date().toLocaleString());
			int count = syllabusService.updateSyllabus(syllabus);
			return count;
		}
		return 0;
	}
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description="删除课程计划进度")
	public @ResponseBody int delete(@PathVariable("id") Integer id) {
		return syllabusService.deleteSyllabus(id);
	}
	
	@RequestMapping(value = "/getOutlineByCourseId", method={RequestMethod.PUT})
	@SystemControllerLog(description="通过课程ID返回大纲")
	public @ResponseBody ResultMessage getClassByEmpId(EduOutline outline){
		ResultMessage rm = new ResultMessage();
		List<EduOutline> someoutLine = new ArrayList<EduOutline>();
		someoutLine = outlineService.selectByDynamic(outline);
		rm.setTotal(someoutLine.size());
		rm.setRows(someoutLine);
		return rm;
	}
	@RequestMapping(value = "/getOutlineByCourseId2", method={RequestMethod.PUT})
	@SystemControllerLog(description="通过课程ID返回大纲")
	public @ResponseBody ResultMessage getClassByEmpId2(EduSyllabus syllabus){
		ResultMessage rm = new ResultMessage();
		List<EduSyllabus> someoutLine = new ArrayList<EduSyllabus>();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = new Emp();
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		syllabus.setEmpId(emp.getId());
		someoutLine = syllabusService.getSomeSyllabus(syllabus);
		rm.setTotal(someoutLine.size());
		rm.setRows(someoutLine);
		return rm;
	}
	@RequestMapping(value = "/getHourFromOutLine", method={RequestMethod.PUT})
	@SystemControllerLog(description="通过大纲ID返回课时数")
	public @ResponseBody ResultMessage getHourFromOutLine(EduOutline outline){
		ResultMessage rm = new ResultMessage();
		List<EduOutline> someoutLine = new ArrayList<EduOutline>();
		someoutLine = outlineService.selectByDynamic(outline);
		rm.setTotal(someoutLine.size());
		rm.setRows(someoutLine);
		return rm;
	}
}
