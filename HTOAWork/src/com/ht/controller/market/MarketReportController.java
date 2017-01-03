package com.ht.controller.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudentCount;
import com.ht.popj.student.StudentFall;
import com.ht.service.market.MarketStudentService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;
@Controller
@RequestMapping("/market/report")
public class MarketReportController {
    @Autowired
    MarketStudentService marketStudentService;
    @Autowired
	StudentInfoService studentInfoService;
	
	@SystemControllerLog(description = "进入意向学生月度报表页面")
	@RequestMapping("/mouthPage")
	public String mouthPage(Model model) throws Exception {
		List<StudentFall> fall = studentInfoService.selectStudentFall();
		List allFall = new ArrayList();
		for (StudentFall f : fall) {
			allFall.add(f.getLevel().replace("届", ""));
		}
		model.addAttribute("allFall", allFall);
		return "/market/Market_mouth_report";
	}
	@SystemControllerLog(description = "进入意向学生年度度报表页面")
	@RequestMapping("/yearPage")
	public String yearPage(Model model) throws Exception {
		List<StudentFall> fall = studentInfoService.selectStudentFall();
		List allFall = new ArrayList();
		for (StudentFall f : fall) {
			allFall.add(f.getLevel().replace("届", ""));
		}
		model.addAttribute("allFall", allFall);
		return "/market/Market_year_report";
	}
	/*
	 * 一年的12个月报表数据
	 */
	@RequestMapping("/getmonth")
	public @ResponseBody ResultMessage getmonth(Integer syear) {
		ResultMessage rm = new ResultMessage();
		MarketStudentCount imsc = marketStudentService.countIntenByYear(syear);//意向
		MarketStudentCount smsc = marketStudentService.countByYearStatus(3, syear);//成功招生
		MarketStudentCount tmsc = marketStudentService.countTestByYear(syear);//已试学学生
		MarketStudentCount lmsc = marketStudentService.countByYearStatus(4, syear);//已离校学生
		if(imsc==null&&smsc==null&&tmsc==null&&lmsc==null){
			rm.setTotal(0);
		}else{
			rm.setTotal(1);
		}
		Map map = new HashMap();
		map.put("imsc", imsc);
		map.put("smsc", smsc);
		map.put("tmsc", tmsc);
		map.put("lmsc", lmsc);
		rm.setRows(map);
		return rm;
	}
	/*
	 * 年份报表数据
	 */
	@RequestMapping("/getyear")
	public @ResponseBody ResultMessage getyear(int startyear, int endyear) {
		ResultMessage rm = new ResultMessage();
		List<Integer> ilist = new ArrayList<Integer>();
		List<Integer> slist = new ArrayList<Integer>();
		List<Integer> tlist = new ArrayList<Integer>();
		List<Integer> llist = new ArrayList<Integer>();
		List<Integer> dataList = new ArrayList<Integer>();
		for(int year = startyear;year<=endyear;year++){
			ilist.add(marketStudentService.reportIntenYearByYear(year));
			slist.add(marketStudentService.reportYearByYearStatus(3, year));//已分班
			tlist.add(marketStudentService.reportTestYearByYear(year));
			llist.add(marketStudentService.reportYearByYearStatus(4, year));//已离校
			dataList.add(year);
		}
		if(ilist==null&&slist==null&&tlist==null&&llist==null){
			rm.setTotal(0);
		}else{
			rm.setTotal(1);
		}
		Map map = new HashMap();
		map.put("ilist", ilist);
		map.put("slist", slist);
		map.put("tlist", tlist);
		map.put("llist", llist);
		map.put("dataList", dataList);
		rm.setRows(map);
		return rm;

	}
}
