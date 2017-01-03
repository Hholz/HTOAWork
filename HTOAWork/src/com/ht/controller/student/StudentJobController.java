package com.ht.controller.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.popj.student.StudentJobs;
import com.ht.service.student.StuJobService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentService;
import com.ht.util.RandomGet;
import com.ht.util.RemarkSet;

@Controller
@RequestMapping("/student/stujob")
public class StudentJobController {

	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	StudentService studentService;
	@Autowired
	StuJobService stuJobService;
	
	/*
	 * 通过接口来获取所有的界别信息，界别里有班级List
	 */
	@RequestMapping("/page")
	@SystemControllerLog(description = "进入学生就业管理页面")
	public String page(Model model) throws Exception{  
		List<StudentFall> fallList = new ArrayList<StudentFall>();
		fallList = studentInfoService.selectStufallclasstwo();
		model.addAttribute("fallList", fallList);
		return "student/jobManage";
	}
	
	
	/*
	 * 通过班级id来获取所有的学生信息stuList，放到页面
	 */
	@RequestMapping(value = "/ifram", method = { RequestMethod.GET })
	@SystemControllerLog(description = "获取学生就业管理右侧ifram页面")
	public String iframPage(){ 
		return "student/testIfram";
	}
	
	
	/*
	 * 通过班级id来获取所有的学生信息stuList，放到页面
	 */
	@RequestMapping(value = "/ifram/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "通过界别id来动态获取ifram页面")
	public String ifram(Model model,@PathVariable("id")Integer id){ 
		//班级信息，里面有界别的对象
		StudentClass stuCls = new StudentClass();
		if(studentInfoService.selectById(id)!=null){
			stuCls = studentInfoService.selectById(id);
		}
		List<Student> stuList = new ArrayList<Student>();
		Student student = new Student();
		student.setClassid(id);
		stuList = studentService.selectByDynamic(student);
		model.addAttribute("stuList", stuList);
		model.addAttribute("stuCls", stuCls);
		return "student/testIfram";
	}
	
	
	/*
	 * 通过班级id来获取所有的学生信息stuList，放到页面
	 */
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	@SystemControllerLog(description = "通过学生id获取就业信息JSON")
	public @ResponseBody StudentJobs getStuJob(Model model,@PathVariable("id")Integer id){ 
		StudentJobs stuJob = stuJobService.selectByStuId(id);
		return stuJob;
	}
	
	
	
	@RequestMapping("/addOrUp")
	@SystemControllerLog(description = "学生就业信息新增或修改")
	public @ResponseBody int addOrUpdate(StudentJobs studentJobs) throws Exception{  
		if(null!=studentJobs){
			if(studentJobs.getId()==null||studentJobs.getId()==0){
				//创建时间
				studentJobs.setCreateTime(new Date());
				studentJobs.setRemark(RemarkSet.getRemark("添加"));
				int count = stuJobService.insertByPJ(studentJobs);
				return count;
			}else{
				//创建时间
				studentJobs.setUpdateTime(new Date());
				studentJobs.setRemark(RemarkSet.getRemark("修改"));
				int count = stuJobService.updateByPJ(studentJobs);
				return count;
			}
		}
		return 0;
	}
	
}
