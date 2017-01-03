package com.ht.controller.education;

import java.text.SimpleDateFormat;
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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduCourseType;
import com.ht.popj.education.EduDept;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.education.CourseService;
import com.ht.service.education.CourseTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/course")
public class CourseController {

	@Autowired
	CourseService courseService;
	
	@Autowired
	CourseTypeService typeService;
	
	@Autowired
	EmpService empService;
	
	@RequestMapping("courseIndex")
	@SystemControllerLog(description = "进入课程信息页面")
	public String courseIndex(Model model){
		//查询课程类型资料
		List<EduCourseType> typeList = typeService.selectByDynamic(null);
		model.addAttribute("typeList", typeList);
		//查询教员员工资料
		List<EduCourse> courseList = courseService.selectByDynamic(null);
		model.addAttribute("courseList", courseList);
		return "education/course_Index";
	}
	@RequestMapping("courseList")
	@SystemControllerLog(description = "查询课程信息表(Json)")
	public @ResponseBody ResultMessage courseList(int limit, int offset, String departmentname, String statu,Model model,EduCourse course){
		ResultMessage rm = new ResultMessage();
		course.setStatus(0);
		List<EduCourse> sList = new ArrayList<EduCourse>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if(course!=null){
			sList = courseService.selectByDynamic(course);
		}else{
			sList = courseService.selectByDynamic(course);
		}
		// 取分页信息
		PageInfo<EduCourse> pageInfo = new PageInfo<EduCourse>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addCourse")
	@SystemControllerLog(description = "新增了一条课程信息")
	public @ResponseBody int addCourse(Model model, EduCourse course){
		//获取系统当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		course.setCreateTime(str);
		int count = courseService.insert(course);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改了一条课程信息")
	public @ResponseBody int updateCCourse(Model model, EduCourse course){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		course.setUpdateTime(str);
		int count = courseService.updateByPrimaryKeySelective(course);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除了一条课程信息")
	public @ResponseBody int deleteCourse(Model model,@PathVariable("id")Integer id){ 
		int count;
		//如果n大于0，说明这个系下面有专业，不能删除
		count =  courseService.deleteByPrimaryKey(id);
		return count;
	}
}
