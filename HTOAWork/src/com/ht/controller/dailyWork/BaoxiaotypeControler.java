package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.Baoxiaotype;
import com.ht.service.dailyWork.BaoxiaotypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/Baoxiaotype")
public class BaoxiaotypeControler {
	@Autowired
	BaoxiaotypeService baoxiaotypeservice;
	
	@RequestMapping("/baoxiaotypelist")
	@SystemControllerLog(description = "进入报销类别管理页面")
	public String seletedepList(Model model){
		return "dailyWork/BaoxiaoType";
	}
	
	//查询报销类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "查询报销类别信息")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Baoxiaotype baoxiaotype) { 
		ResultMessage rm = new ResultMessage();
		List<Baoxiaotype> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = baoxiaotypeservice.Baoxiaotypelist(baoxiaotype);
        PageInfo<Baoxiaotype> pageInfo = new PageInfo<Baoxiaotype>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有报销类别：" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//新增报销类别
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "新增报销类别")
	public @ResponseBody int addemp(Baoxiaotype baoxiaotype) {
		baoxiaotype.setCreateTime(new Date());
		baoxiaotype.setUpdateTime(new Date());
		int count=baoxiaotypeservice.insert(baoxiaotype);
		return count; 
	}
	//修改报销类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "修改报销类别信息")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Baoxiaotype baoxiaotype) { 
		baoxiaotype.setUpdateTime(new Date());
		int count=baoxiaotypeservice.updateByPrimaryKey(baoxiaotype);
		return count; 
	}
	//删除报销类别
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "删除报销类别信息")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		int count=baoxiaotypeservice.deleteByPrimaryKey(id);
		return count; 
	}
}
