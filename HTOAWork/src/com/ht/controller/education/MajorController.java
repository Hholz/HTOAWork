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
import com.ht.popj.education.EduDept;
import com.ht.popj.education.EduMajor;
import com.ht.popj.education.EduSeminar;
import com.ht.popj.student.StudentClass;
import com.ht.service.education.DeptService;
import com.ht.service.education.MajorService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/education/major")
public class MajorController {

	@Autowired
	MajorService majorService;
	
	@Autowired
	DeptService deptService;
	
	@Autowired
	StudentInfoService studentInfoService;
	
	@RequestMapping("majorIndex")
	@SystemControllerLog(description = "进入专业信息页面")
	public String deptIndex(Model model){
		List<EduDept> depList = deptService.selectByDynamic(null);
		model.addAttribute("depList", depList);
		return "education/major_Index";
	}
	
	@RequestMapping("/addMajor")
	@SystemControllerLog(description = "新增了一条专业信息")
	public @ResponseBody int addMajor(Model model, EduMajor major){
		//获取系统当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		major.setCreateTime(str);
		int count = majorService.insert(major);
		List<EduMajor> majorList = new ArrayList<EduMajor>();
		model.addAttribute("mList", majorList);
		return count;
	}
	@RequestMapping("majorList")
	@SystemControllerLog(description = "查询了专业信息表(Json)")
	public @ResponseBody ResultMessage majorList(int limit, int offset, Model model,EduMajor major){
		ResultMessage rm = new ResultMessage();
		List<EduMajor> sList = new ArrayList<EduMajor>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		
		sList = majorService.selectByDynamic(major);
		// 取分页信息
		PageInfo<EduMajor> pageInfo = new PageInfo<EduMajor>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改了一条专业信息")
	public @ResponseBody int updateMajor(Model model, EduMajor major){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		major.setUpdateTime(str);
		int count = majorService.updateByPrimaryKeySelective(major);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除了一条专业信息")
	public @ResponseBody int deleteMajor(@PathVariable("id")Integer id){  
		int count=0;
		StudentClass  cls = new StudentClass();
		cls.setMajorId(id);
		List<StudentClass> list = studentInfoService.selectStudentclass(cls);
		if(list.size()!=0){//如果查询出来有数据，不能删除
			count = -1;
		}else{
			count = majorService.deleteByPrimaryKey(id);
		}
		return count;
	}
}
