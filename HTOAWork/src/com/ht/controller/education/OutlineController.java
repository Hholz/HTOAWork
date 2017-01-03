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
import com.ht.popj.education.EduCourse;
import com.ht.popj.education.EduOutline;
import com.ht.popj.education.EduTerm;
import com.ht.service.education.CourseService;
import com.ht.service.education.OutlineService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("education/outline")
public class OutlineController {

	@Autowired
	OutlineService outlineService;
	
	@Autowired
	CourseService courseService;
	
	@RequestMapping("outlineIndex")
	@SystemControllerLog(description = "进入课程大纲页面")
	public String outlineIndex(Model model){
		List<EduCourse> courseList = new ArrayList<EduCourse>();
		EduCourse course = new EduCourse();
		course.setStatus(0);
		courseList = courseService.selectByDynamic(course);
		model.addAttribute("courseList", courseList);
		return "education/outline_Index";
	}
	@RequestMapping("/addOutline")
	@SystemControllerLog(description = "新增了一条课程大纲信息")
	public @ResponseBody int  addOutline(Model model, EduOutline outline){
		//获取系统当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		outline.setCreateTime(str);
		int count = outlineService.insert(outline);
		return count;
	}
	@RequestMapping("outlineList")
	@SystemControllerLog(description = "查询课程大纲表(Json)")
	public @ResponseBody ResultMessage outlineList(int limit, int offset, String departmentname, String statu,Model model,EduOutline outline){
		ResultMessage rm = new ResultMessage();
		outline.setStatus(0);
		List<EduOutline> sList = new ArrayList<EduOutline>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if(outline!=null){
			sList = outlineService.selectByDynamic(outline);
		}else{
			sList = outlineService.selectByDynamic(outline);
		}
		// 取分页信息
		PageInfo<EduOutline> pageInfo = new PageInfo<EduOutline>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改了一条课程大纲信息")
	public @ResponseBody int updateOutline(Model model, EduOutline outline){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		outline.setUpdateTime(str);
		System.out.println(outline);
		int count = outlineService.updateByPrimaryKey(outline);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除了一条课程大纲信息")
	public @ResponseBody int deleteOutline(Model model,@PathVariable("id")Integer id){ 
		int count =  outlineService.deleteByPrimaryKey(id);
		return count;
	}
	
}
