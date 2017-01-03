package com.ht.controller.student;


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
import com.ht.popj.dailyWork.Noticetype;
import com.ht.popj.finance.FinanceFeestandard;
import com.ht.popj.student.StuRepSet;
import com.ht.service.student.StuRepSetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/StuRepSet")
public class StuRepSetController {
	@Autowired
	StuRepSetService sturepsetService;
	
	@RequestMapping("/sturepsetList")
	@SystemControllerLog(description = "进入打分模板信息页面")
	public String noticeList(Model model){
		return "/student/StuRepSet";
	}
	
	@RequestMapping("/sturepsetListJson")
	@SystemControllerLog(description = "返回打分模板表json数据")
	public @ResponseBody ResultMessage noticeTypeListJson(int limit, int offset,StuRepSet sturepset){
		ResultMessage rm = new ResultMessage();
		List<StuRepSet> list = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		
		list = sturepsetService.selectStuRepSet(sturepset);
		
		 // 取分页信息
        PageInfo<StuRepSet> pageInfo = new PageInfo<StuRepSet>(list);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	
	@RequestMapping("/addStuRepSet")
	@SystemControllerLog(description = "新增打分模板")
	public @ResponseBody int addStuRepSet(StuRepSet sturepset){
		sturepset.setCreateTime(new Date());
		if(sturepset != null){
			int count = sturepsetService.insertStuRepSet(sturepset);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value="/stuRepSet/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改打分模板")
	public @ResponseBody int updateStuRepSet(StuRepSet sturepset){
		sturepset.setUpdateTime(new Date());
		if(sturepset != null){
			int count = sturepsetService.updateStuRepSet(sturepset);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value="/stuRepSet/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除打分模板")
	public @ResponseBody int deleteStuRepSet(@PathVariable("id") Integer id){
		int count = sturepsetService.deleteStuRepSet(id);
		return count;
	}
	@RequestMapping("/findAll")
	public @ResponseBody List<StuRepSet> getAll(Model model){
		List<StuRepSet> list = new ArrayList<StuRepSet>();
		list = sturepsetService.selectStuRepSet(new StuRepSet());
		return list;
	}
}
