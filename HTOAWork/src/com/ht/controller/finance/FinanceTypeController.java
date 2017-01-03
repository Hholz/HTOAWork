package com.ht.controller.finance;

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
import com.ht.popj.education.EduSeminar;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.finance.FinanceType;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("finance/financeType")
public class FinanceTypeController {

	@Autowired
	FinanceTypeService financeTypeService;
	
	@Autowired
	FinanceBalanceService financeBalanceService;
	
	@RequestMapping("financeTypeIndex")
	@SystemControllerLog(description = "进入收支类别信息页面")
	public String courseIndex(Model model){
		return "finance/finance_paytype";
	}
	@RequestMapping("paytypeList")
	@SystemControllerLog(description = "查询收支类别信息表(Json)")
	public @ResponseBody ResultMessage paytypeList(int limit, int offset,Model model,FinanceType type){
		ResultMessage rm = new ResultMessage();
		List<FinanceType> sList = new ArrayList<FinanceType>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		sList = financeTypeService.selectByDynamic(type);
		// 取分页信息
		PageInfo<FinanceType> pageInfo = new PageInfo<FinanceType>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addPayType")
	@SystemControllerLog(description = "新增了一条收支类别信息")
	public @ResponseBody int addPayType(Model model, FinanceType type){
		//获取系统当前时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		type.setCreateTime(str);
		int count = financeTypeService.insert(type);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改了一条收支类别信息")
	public @ResponseBody int updatePayType(Model model, FinanceType type){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		type.setUpdateTime(str);
		int count = financeTypeService.updateByPrimaryKeySelective(type);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除了一条支付类别信息")
	public @ResponseBody int deletePayType(Model model,@PathVariable("id")Integer id){ 
		int count;
		//如果n大于0，说明这个系下面有专业，不能删除
		count =  financeTypeService.deleteByPrimaryKey(id);
		return count;
	}
}
