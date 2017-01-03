package com.ht.controller.finance;

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
import com.ht.popj.education.EduTerm;
import com.ht.popj.finance.FinanceTuitionset;
import com.ht.service.education.TermService;
import com.ht.service.finance.FinanceTuitionSetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("finance/FinanceTuitionSet")
public class FinanceTuitionSetController {

	@Autowired
	FinanceTuitionSetService financeTuitionsetservice;
	@Autowired
	TermService termService;
	@RequestMapping("financeTuitionSet")
	@SystemControllerLog(description = "进入学费设置页面")
	public String FinanceTuitionSet(Model model) throws Exception{
		List<EduTerm> term = new ArrayList<EduTerm>();
		term = termService.selectTermAll();
		model.addAttribute("term",term);
		return "";
	}
	
	@RequestMapping("Json")
	public @ResponseBody ResultMessage listJson(int limit, int offset,FinanceTuitionset stu){
		ResultMessage rm = new ResultMessage();
		List<FinanceTuitionset> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(stu!=null && !stu.equals("")){
			sList = financeTuitionsetservice.selectDinamic(stu);
		}else{
			sList = financeTuitionsetservice.selectAll();
		}
		 // 取分页信息
        PageInfo<FinanceTuitionset> pageInfo = new PageInfo<FinanceTuitionset>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//添加
		@RequestMapping("/add")
		public @ResponseBody int addStudent(Model model,FinanceTuitionset tuition) throws Exception{ 
			
			if(null!=tuition){
				int count = financeTuitionsetservice.insert(tuition);
				return count;
			}
			//当student为空时，运行到这里，返回0
			return 0;
		}
		
		@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
		public @ResponseBody int deleteStudentOption(Model model,@PathVariable("id")Integer id){  
			int count = financeTuitionsetservice.deleteByPrimaryKey(id);
			return count;
		}
		@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
		public @ResponseBody int updateStudent(Model model,FinanceTuitionset student) throws Exception{  
			if(null!=student){
				int count = financeTuitionsetservice.updateByPrimaryKeySelective(student);
				return count;
			}
			return 0;
		}
}
