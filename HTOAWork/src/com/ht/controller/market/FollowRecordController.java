package com.ht.controller.market;

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
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.market.MarketFollow;
import com.ht.popj.market.TrackStudent;
import com.ht.service.market.MarketFollowService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/market/followrecord")
public class FollowRecordController {
	@Autowired
	MarketFollowService marketfollowService;
	
	@RequestMapping(value = "/page/{id}")
	@SystemControllerLog(description = "通过学生id进入学生跟踪信息页面")
	public String page(Model model,@PathVariable("id") Integer id){
		model.addAttribute("stuId", id);
		return "market/FollowRecordStudent";
	}
	
	//跟踪学生列表
	@RequestMapping("/marketrecordListJson")
	@SystemControllerLog(description = "返回学生跟踪表json数据")
	public @ResponseBody ResultMessage trackStudentListJson(int limit, int offset,MarketFollow marketfllow){
		ResultMessage rm = new ResultMessage();
		List<MarketFollow> list = new ArrayList<>();
		//计算页数
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//该页数是从1开始（当前页数，一页显示的条数）
		if(marketfllow != null){
			list = marketfollowService.findfllowList1(marketfllow);
		}else{
			list = marketfollowService.findfllowList2();
		}
		// 取分页信息
        PageInfo<MarketFollow> pageInfo = new PageInfo<MarketFollow>(list);
        long total = pageInfo.getTotal(); //获取总记录数
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	
	//新增跟踪情况
	@RequestMapping("/addMarketfllow")
	@SystemControllerLog(description = "新增学生跟踪")
	public @ResponseBody int addMarketfllow(MarketFollow marketfllow){
		marketfllow.setCreateTime(new Date().toLocaleString());
		marketfllow.setTracktime(new Date().toLocaleString());
		if(marketfllow != null){
			int count = marketfollowService.addfllow(marketfllow);
			return count;
		}
		return 0;
	}
	
	//修改跟踪学生
	@RequestMapping(value="/marketrecord/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "修改学生跟踪")
	public @ResponseBody int updateTrackStudent(MarketFollow marketfllow){
		marketfllow.setUpdateTime(new Date().toLocaleString());
		marketfllow.setTracktime(new Date().toLocaleString());
		if(marketfllow != null){
			int count = marketfollowService.updatefllow(marketfllow);
			return count;
		}
		return 0;
	}
	
	//删除跟踪学生
	@RequestMapping(value="/marketrecord/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "删除学生跟踪")
	public @ResponseBody int deleteTrackStudent(@PathVariable("id") Integer id){
		int count = marketfollowService.deletefllow(id);
		return count;
	}
}
