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
@RequestMapping("/market/follow")
public class FollowController {

	@Autowired
	MarketStudentService msService;
	
	@SystemControllerLog(description = "进入意向学生跟踪页面")
	@RequestMapping("/page")
	public String page() {
		return "/market/followStudent";
	}
	@SystemControllerLog(description = "进入意向学生跟踪页面")
	@RequestMapping("/history")
	public String history() {
		return "/market/FollowStuHistory";
	}
	
	// 要跟踪的意向学生的列表
	@RequestMapping("/followStudentListJson")
	@SystemControllerLog(description = "返回要跟踪MarketStudent学生表json数据")
	public @ResponseBody ResultMessage followStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (marketStudent != null) {
			// 取出当前招生老师的所录入的所有需要跟踪的意向学生的列表
			list = msService.selectFollowStudent(marketStudent);
		}
		// 取分页信息
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	// 查看跟踪的历史记录
	@RequestMapping("/followStudentListJsonHistory")
	@SystemControllerLog(description = "返回要所有跟踪MarketStudent学生表json数据")
	public @ResponseBody ResultMessage followStudentListJsonHistory(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		if (marketStudent != null) {
			// 取出当前招生老师的所录入的所有需要跟踪的意向学生的列表
			list = msService.selectAllByPJ(marketStudent);
		}
		// 取分页信息
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // 获取总记录数
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

}
