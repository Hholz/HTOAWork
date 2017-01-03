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
	@SystemControllerLog(description = "����γ̴��ҳ��")
	public String outlineIndex(Model model){
		List<EduCourse> courseList = new ArrayList<EduCourse>();
		EduCourse course = new EduCourse();
		course.setStatus(0);
		courseList = courseService.selectByDynamic(course);
		model.addAttribute("courseList", courseList);
		return "education/outline_Index";
	}
	@RequestMapping("/addOutline")
	@SystemControllerLog(description = "������һ���γ̴����Ϣ")
	public @ResponseBody int  addOutline(Model model, EduOutline outline){
		//��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		outline.setCreateTime(str);
		int count = outlineService.insert(outline);
		return count;
	}
	@RequestMapping("outlineList")
	@SystemControllerLog(description = "��ѯ�γ̴�ٱ�(Json)")
	public @ResponseBody ResultMessage outlineList(int limit, int offset, String departmentname, String statu,Model model,EduOutline outline){
		ResultMessage rm = new ResultMessage();
		outline.setStatus(0);
		List<EduOutline> sList = new ArrayList<EduOutline>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(outline!=null){
			sList = outlineService.selectByDynamic(outline);
		}else{
			sList = outlineService.selectByDynamic(outline);
		}
		// ȡ��ҳ��Ϣ
		PageInfo<EduOutline> pageInfo = new PageInfo<EduOutline>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���һ���γ̴����Ϣ")
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
	@SystemControllerLog(description = "ɾ����һ���γ̴����Ϣ")
	public @ResponseBody int deleteOutline(Model model,@PathVariable("id")Integer id){ 
		int count =  outlineService.deleteByPrimaryKey(id);
		return count;
	}
	
}
