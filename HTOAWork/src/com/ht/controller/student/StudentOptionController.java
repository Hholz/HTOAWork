package com.ht.controller.student;
import java.text.ParseException;
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
import com.ht.popj.finance.FinanceFeestandard;
import com.ht.popj.student.StuOptionSort;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFloor;
import com.ht.popj.student.StudentOption;
import com.ht.service.student.StuOptionSortService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentOptionService;
import com.ht.service.student.StudentService;
import com.ht.util.RandomGet;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;


@Controller
@RequestMapping("/student/studentOption")
public class StudentOptionController {

	@Autowired
	StuOptionSortService stuOptionSortService;
	@Autowired 
	StudentInfoService studentInfoService;
	@Autowired 
	StudentService studentService;
	@Autowired
	StudentOptionService studentOptionService;
	@RequestMapping("/studentOption")
	@SystemControllerLog(description = "进入学生意见反馈页面")
	public String studentOption(Model model) throws Exception{
		List<StuOptionSort> stuSort= stuOptionSortService.selectDynamic(null);
		model.addAttribute("stuSort", stuSort);
//		List<StudentClass> studentclass = studentInfoService.selectallstduentclass();
//		model.addAttribute("studentclass", studentclass);
		if(null != UserInfoUtil.getStudent()){
			Student stu = UserInfoUtil.getStudent();
			StudentOption option = new StudentOption();
			option.setStudentname(stu.getStuname());
			option.setClassname(stu.getClassid()+"");
			List<StudentOption> list = studentOptionService.selectDynamic(option);
			
			model.addAttribute("optionlist", list);
		}
		return "/student/studentOption";
	}
	
	@RequestMapping("/findAllClass")
	public @ResponseBody List<StuOptionSort> getAll(Model model){
		List<StuOptionSort> list = new ArrayList<StuOptionSort>();
		list = stuOptionSortService.selectDynamic(null);
		return list;
	}
	@RequestMapping("/addoption")
	@SystemControllerLog(description = "建议新增")
	public @ResponseBody int addOption(Model model,StudentOption option) throws ParseException{ 
		
		if(null!=option){
			//创建时间
			option.setCreatetime(new Date());// new Date()为获取当前系统时间
			option.setRemark("添加建议");
			if(null != UserInfoUtil.getStudent()){
				Student stu = UserInfoUtil.getStudent();
				
				option.setStudentname(stu.getStuname());
				option.setClassname(stu.getClassid()+"");
				option.setStuid(stu.getStuno());
			}else{
				return 0;
			}
			int count = studentOptionService.insertSelective(option);
			return count;
		}
		//当option为空时，运行到这里，返回0
		return 0;
	}
	
	//删除意见
	@RequestMapping(value = "/{id}")
	@SystemControllerLog(description = "删除意见")
	public String delete(@PathVariable("id") Integer id,Model model) throws Exception{
		studentOptionService.deleteByPrimaryKey(id);
		return "redirect:/student/studentOption/studentOption";
	}
	
	//条件查询
	@RequestMapping(value = "/select/{id}")
	@SystemControllerLog(description = "删除意见")
	public String select(@PathVariable("id") Integer id,Model model) throws Exception{
		StuOptionSort sort = stuOptionSortService.selectByPrimaryKey(id);
		StudentOption op = new StudentOption();
		op.setTitleclassid(sort.getSortname());
		List<StudentOption> list = studentOptionService.selectDynamic(op);
		model.addAttribute("optionlist", list);
		List<StuOptionSort> stuSort= stuOptionSortService.selectDynamic(null);
		model.addAttribute("stuSort", stuSort);
		
		return "/student//studentOption";
	}
	
}
