package com.ht.controller.student;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.student.StuOptionSort;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentOption;
import com.ht.service.student.StuOptionSortService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.student.StudentOptionService;
import com.ht.service.student.StudentService;
import com.ht.util.UserInfoUtil;

@Controller
@RequestMapping("/student/stuOptionList")
public class StuOptionListController {

	@Autowired
	StuOptionSortService stuOptionSortService;
	@Autowired 
	StudentInfoService studentInfoService;
	@Autowired 
	StudentService studentService;
	@Autowired
	StudentOptionService studentOptionService;
	@RequestMapping("/studentOptionList")
	@SystemControllerLog(description = "进入学生意见反馈页面")
	public String studentOption(Model model) throws Exception{
		List<StuOptionSort> stuSort= stuOptionSortService.selectDynamic(null);
		model.addAttribute("stuSort", stuSort);
		
		List<StudentOption> list = studentOptionService.selectDynamic(null);
		model.addAttribute("optionlist", list);
		return "/student/studentOptionList";
	}
	
	//条件查询
	@RequestMapping(value = "/{id}")
	@SystemControllerLog(description = "条件查询")
	public String selectSort(@PathVariable("id") Integer id,Model model) throws Exception{
		StuOptionSort sort = stuOptionSortService.selectByPrimaryKey(id);
		StudentOption op = new StudentOption();
		op.setTitleclassid(sort.getSortname());
		List<StudentOption> list = studentOptionService.selectDynamic(op);
		model.addAttribute("optionlist", list);
		List<StuOptionSort> stuSort= stuOptionSortService.selectDynamic(null);
		model.addAttribute("stuSort", stuSort);
		return "/student/studentOptionList";
	}
		
}
