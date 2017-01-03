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
import com.ht.popj.finance.FinaceFeedbackset;
import com.ht.service.finance.FinanceFeedbackdetailService;
import com.ht.service.finance.FinanceFeedbacksetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/FeedBackSet")

public class FinanceFeedBackSetControllor {

	@Autowired FinanceFeedbacksetService financefeedbacksetService;
	@Autowired FinanceFeedbackdetailService financeFeedbackdetailService;
	@RequestMapping("/feedback")
	@SystemControllerLog(description = "进入反馈模板页面")
	public String feedback(Model model) throws Exception{
		List<FinaceFeedbackset> financeFeedBackList = financefeedbacksetService.selectFinanceFeedbacksetAll();
		model.addAttribute("financeFeedBackList", financeFeedBackList);
		return "/finance/finance_FeedBackSet";
	}
	@RequestMapping("financefeedbacklistJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,FinaceFeedbackset feedback){
		ResultMessage rm = new ResultMessage();
		List<FinaceFeedbackset> sList = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(feedback.getId()!=null){
			sList = financefeedbacksetService.selectFinanceFeedbacksetByDynamic(feedback);
		}else{
			sList = financefeedbacksetService.selectFinanceFeedbacksetAll();
		}
		System.out.println(sList.size());
		 // 取分页信息
        PageInfo<FinaceFeedbackset> pageInfo = new PageInfo<FinaceFeedbackset>(sList);
        long total = pageInfo.getTotal(); //获取总记录数
        System.out.println("共有反馈条目：" + total);
       // System.err.println(sList.get(0).getFloorAdmin());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addfinancefeedback")
	public @ResponseBody int  addfinancefeedback(Model model, FinaceFeedbackset finance){
		if(null!=finance){
			if(null==finance.getName()||finance.getName().isEmpty()){
				//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
				return 0;
			}
			
			int count = financefeedbacksetService.insert(finance);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	//修改反馈模板
		@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
		public @ResponseBody int updatefinancefeedback(Model model,FinaceFeedbackset finance){  
			if(null!=finance){
				if(null==finance.getName()||finance.getName().isEmpty()){
					//返回0，在Demo.jsp中ajax回调函数中用来判断是否新增成功
					return 0;
				}
				
				int count = financefeedbacksetService.updateByPrimaryKeySelective(finance);
				return count;
			}
			return 0;
		}
		
		//删除模板，
		@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
		public @ResponseBody int delete(Model model,@PathVariable("id")Integer id){
			//实际是修改状态，调用修改方法
			int count = financefeedbacksetService.deleteByPrimaryKey(id);
			return count;
		}
		
}
