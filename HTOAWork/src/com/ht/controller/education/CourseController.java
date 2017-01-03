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
	@SystemControllerLog(description = "����γ���Ϣҳ��")
	public String courseIndex(Model model){
		//��ѯ�γ���������
		List<EduCourseType> typeList = typeService.selectByDynamic(null);
		model.addAttribute("typeList", typeList);
		//��ѯ��ԱԱ������
		List<EduCourse> courseList = courseService.selectByDynamic(null);
		model.addAttribute("courseList", courseList);
		return "education/course_Index";
	}
	@RequestMapping("courseList")
	@SystemControllerLog(description = "��ѯ�γ���Ϣ��(Json)")
	public @ResponseBody ResultMessage courseList(int limit, int offset, String departmentname, String statu,Model model,EduCourse course){
		ResultMessage rm = new ResultMessage();
		course.setStatus(0);
		List<EduCourse> sList = new ArrayList<EduCourse>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(course!=null){
			sList = courseService.selectByDynamic(course);
		}else{
			sList = courseService.selectByDynamic(course);
		}
		// ȡ��ҳ��Ϣ
		PageInfo<EduCourse> pageInfo = new PageInfo<EduCourse>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addCourse")
	@SystemControllerLog(description = "������һ���γ���Ϣ")
	public @ResponseBody int addCourse(Model model, EduCourse course){
		//��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		course.setCreateTime(str);
		int count = courseService.insert(course);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���һ���γ���Ϣ")
	public @ResponseBody int updateCCourse(Model model, EduCourse course){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		course.setUpdateTime(str);
		int count = courseService.updateByPrimaryKeySelective(course);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����һ���γ���Ϣ")
	public @ResponseBody int deleteCourse(Model model,@PathVariable("id")Integer id){ 
		int count;
		//���n����0��˵�����ϵ������רҵ������ɾ��
		count =  courseService.deleteByPrimaryKey(id);
		return count;
	}
}
