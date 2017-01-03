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
import com.ht.popj.finance.FinanceFeedbackdetail;
import com.ht.service.finance.FinanceFeedbackdetailService;
import com.ht.util.ResultMessage;


@Controller
@RequestMapping("/finance/financeFeedBackDetail")
public class FinanceFeedBackdetailController {

	@Autowired
	FinanceFeedbackdetailService financeFeedbackdetailService;
	
	@RequestMapping("financeFeedBackDetailJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,FinanceFeedbackdetail feedback){
		ResultMessage rm = new ResultMessage();
		List<FinanceFeedbackdetail> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		
		sList = financeFeedbackdetailService.selectDynamic(feedback);
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<FinanceFeedbackdetail> pageInfo = new PageInfo<FinanceFeedbackdetail>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有反馈条目：" + total);
       // System.err.println(sList.get(0).getFloorAdmin());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/add")
	public @ResponseBody int  addfinancefeedbackdetail(Model model, FinanceFeedbackdetail finance){
		if(null!=finance){
			
			int count = financeFeedbackdetailService.insert(finance);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//修改反馈明细
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatefinancefeedback(Model model,FinanceFeedbackdetail finance){  
		if(null!=finance){
			int count = financeFeedbackdetailService.updateByPrimaryKeySelective(finance);
			return count;
		}
		return 0;
	}
	
	//删除模板，
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deletefinance(Model model,@PathVariable("id")Integer id){
		//实际是修改状态，调用修改方法
				int count = financeFeedbackdetailService.deleteByPrimaryKey(id);
				return count;
	}
}
