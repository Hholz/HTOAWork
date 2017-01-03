package com.ht.controller.market;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudent;
import com.ht.service.market.MarketStudentService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/market/allStudent")
public class MarketStudentAllController {

	//市场部里意向预定学生的表service
	@Autowired
	MarketStudentService msService;
	
	@SystemControllerLog(description = "进入所有意向学生信息页面")
	@RequestMapping("/page")
	public String intentionStudentList() {
		return "/market/MarketStudentAll";
	}
	// 意向学生列表
	@RequestMapping("/intentionStudentListJson")
	@SystemControllerLog(description = "返回意向学生表json数据")
	public @ResponseBody ResultMessage intentionStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (marketStudent != null) {
			if(null!=marketStudent.getMsStatus()){
				if(marketStudent.getMsStatus()==100){
					//全部
					marketStudent.setMsStatus(null);
					list = msService.selectIntentionStudent(marketStudent);
				}else{
					list = msService.selectPredStudentAll(marketStudent);
				}
			}else{
				list = msService.selectIntentionStudent(marketStudent);
			}
		}
		// 取分页信息
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
}
