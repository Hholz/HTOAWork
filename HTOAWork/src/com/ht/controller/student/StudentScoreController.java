package com.ht.controller.student;

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
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduCourse;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.StuReplyModelD;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentScore;
import com.ht.service.dailyWork.EducationService;
import com.ht.service.education.CourseService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentScoreService;
import com.ht.service.student.StudentService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/studentScore")
public class StudentScoreController {

	@Autowired
	StudentScoreService studentScoreService;
	@Autowired
	StudentService studentService;
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	CourseService courseService;
	@RequestMapping("/studentScore")
	@SystemControllerLog(description = "进入成绩记录页面")
	public String studentSocre(Model model) throws Exception{
		
		List<Student>  student = studentService.selectStudentAll();
		List<StudentClass> studentclass = studentInfoService.selectallstduentclass();
		List<EduCourse> course = courseService.selectCourseAll();
		model.addAttribute("student", student);
		model.addAttribute("studentclass",studentclass);
		model.addAttribute("course",course);
		return "student/studentScore";
	}
	
	/*
	 * 进入学生表格json数据
	 */
	@RequestMapping("/studentJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "成绩记录StudentScoreController里的list学生表")
	public @ResponseBody ResultMessage repModelJsonList(int limit, int offset,Student score){
		ResultMessage rm = new ResultMessage();
		List<Student> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = studentService.selectStudentAll();
		 // 取分页信息
        PageInfo<Student> pageInfo = new PageInfo<Student>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * 得分表格json数据
	 */
	@RequestMapping("/scoreJson")
	//此处为记录AOP拦截Controller记录用户操作    
	@SystemControllerLog(description = "成绩记录StudentScoreController里的list登记分数表")
	public @ResponseBody ResultMessage ScoreJsonList(int limit, int offset,StudentScore score){
		ResultMessage rm = new ResultMessage();
		List<StudentScore> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(null!=score){
			sList = studentScoreService.selectDinamic(score);
		}else{
			sList = studentScoreService.selectAll();
		}
			
		
		 // 取分页信息
        PageInfo<StudentScore> pageInfo = new PageInfo<StudentScore>(sList);
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
	
	@RequestMapping("/addscore")
	public @ResponseBody int addScore(StudentScore score) throws Exception{ 
		//生成随机密码
		if(null!=score){
			int count = studentScoreService.insert(score);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentScore(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
		int count = studentScoreService.deleteByPrimaryKey(id);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateRepModelD(StudentScore srmd) throws Exception{  
		if(null!=srmd){
			int count = studentScoreService.updateByPrimaryKeySelective(srmd);
			return count;
		}
		return 0;
	}
}
