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
import com.ht.popj.education.EduCourseType;
import com.ht.popj.education.EduOutline;
import com.ht.service.education.CourseService;
import com.ht.service.education.CourseTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/coursetype")
public class CourseTypeController {

	@Autowired
	CourseTypeService typeService;
	
	@Autowired
	CourseService courseService;
	
	@RequestMapping("typeIndex")
	@SystemControllerLog(description = "����γ������Ϣҳ��")
	public String typeIndex(){
		return "education/courseType_Index";
	}
	@RequestMapping("typeList")
	@SystemControllerLog(description = "��ѯ�γ������Ϣ(Json)")
	public @ResponseBody ResultMessage typeList(int limit, int offset, String departmentname, String statu,Model model,EduCourseType type){
		ResultMessage rm = new ResultMessage();
		type.setStatus(0);
		List<EduCourseType> sList = new ArrayList<EduCourseType>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(type!=null){
			sList = typeService.selectByDynamic(type);
		}else{
			sList = typeService.selectByDynamic(type);
		}
		// ȡ��ҳ��Ϣ
		PageInfo<EduCourseType> pageInfo = new PageInfo<EduCourseType>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addType")
	@SystemControllerLog(description = "������һ���γ������Ϣ")
	public @ResponseBody int  addType(Model model, EduCourseType type){
		//��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		type.setCreateTime(str);
		int count =	typeService.insert(type);
		
		List<EduCourseType> courseList = new ArrayList<EduCourseType>();
		model.addAttribute("mList", courseList);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���һ���γ������Ϣ")
	public @ResponseBody int updateType(Model model, EduCourseType dept){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		dept.setUpdateTime(str);
		int count = typeService.updateByPrimaryKeySelective(dept);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����һ���γ������Ϣ")
	public @ResponseBody int deleteType(Model model,@PathVariable("id")Integer id){ 
		int count;
		int n = courseService.getCount(id);
		if(n>0){
			count=2;
		}else{
			count =  typeService.deleteByPrimaryKey(id);
		}
		return count;
	}
}
