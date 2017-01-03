package com.ht.controller.sysSet;

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
import com.ht.popj.sysSet.FinanceSalarytypese;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.sysSet.SalTypeService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet/salType")
public class salTypeSetController {

	@Autowired
	SalTypeService salTypeService;
	@RequestMapping("/page")
	public String page(){  
		return "sysSet/salaryTypeSet";
	}
	/*
	 * 工资类别表格json数据
	 */
	@RequestMapping("/salTypeListJson")
	public @ResponseBody ResultMessage salTypeListJson(int limit, int offset,FinanceSalarytypese salType){
		ResultMessage rm = new ResultMessage();
		List<FinanceSalarytypese> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		sList = salTypeService.selectAll();
		 // 取分页信息
        PageInfo<FinanceSalarytypese> pageInfo = new PageInfo<FinanceSalarytypese>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * 添加工资类别
	 */
	@RequestMapping("/addSalType")
	public @ResponseBody int addSalType(FinanceSalarytypese salType) throws ParseException{ 
		//创建时间
		salType.setCreateTime(new Date());// new Date()为获取当前系统时间
		//生成随机密码
		salType.setRemark(RemarkSet.getRemark("添加"));
		if(null!=salType){
			int count = salTypeService.insertByPJ(salType);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	/*
	 * 修改工资类别
	 */
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateSalType(FinanceSalarytypese salType) throws ParseException{  
		salType.setRemark(RemarkSet.getRemark("更新"));
		salType.setUpdateTime(new Date());
		if(null!=salType){
			int count = salTypeService.updateByPJ(salType);
			return count;
		}
		return 0;
	}
	/*
	 * 删除工资类别
	 */
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteSalType(Model model,@PathVariable("id")Integer id){  
		int count = salTypeService.delByUpdate(id);
		return count;
	}
}
