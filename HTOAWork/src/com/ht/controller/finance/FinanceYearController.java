package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.finance.FinanceMonth;
import com.ht.popj.finance.FinanceYear;
import com.ht.popj.student.StudentFall;
import com.ht.service.finance.FinanceYearService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/year")
public class FinanceYearController {

	@Autowired
	FinanceYearService financeYearService;
	@Autowired
	StudentInfoService studentInfoService;

	@RequestMapping("/list")
	@SystemControllerLog(description = "进入财务年度报表页面")
	public String list(Model model) throws Exception {
		List<StudentFall> fall = studentInfoService.selectStudentFall();
		List allFall = new ArrayList();
		for (StudentFall f : fall) {
			allFall.add(f.getLevel().replace("届", ""));
		}
		model.addAttribute("allFall", allFall);
		return "finance/finance_year_report";
	}

	@RequestMapping("getyearin")
	@SystemControllerLog(description = "进入财务年度报表页面")
	public @ResponseBody ResultMessage getYearin(int startyear, int endyear) {
		ResultMessage rm = new ResultMessage();
		List<FinanceYear> fyin = financeYearService.getYearIN(startyear, endyear);
		for (int i = 0; i < fyin.size(); i++) {
			FinanceYear f = fyin.get(i);
			if (f.getStatu() == 0) {
				f.setYsumin(f.getMoney());
				for (int j = 0; j < fyin.size(); j++) {
					FinanceYear y = fyin.get(j);
					if (i != j && f.getSdate() == y.getSdate() && f.getYsumin() != 0 && f.getYsumout() == 0) {
						f.setYsumout(y.getMoney());
						fyin.remove(j);
						fyin.set(i, f);
					}
				}
			} else if (f.getStatu() == 2) {
				f.setYsumout(f.getMoney());
				fyin.set(i, f);
			}
		}
		rm.setTotal(fyin.size());
		rm.setRows(fyin);
		return rm;

	}

	@RequestMapping("getyearinout")
	@SystemControllerLog(description = "进入财务年度报表页面")
	public @ResponseBody ResultMessage getYearout(int startyear, int endyear) {
		ResultMessage rm = new ResultMessage();
		List<FinanceYear> fyout = financeYearService.getYearOut(startyear, endyear);
		rm.setRows(fyout);
		rm.setTotal(fyout.size());
		return rm;

	}

	@RequestMapping("/listmonth")
	@SystemControllerLog(description = "进入财务月度报表页面")
	public String listmonth(Model model) throws Exception {
		List<StudentFall> fall = studentInfoService.selectStudentFall();
		List allFall = new ArrayList();
		for (StudentFall f : fall) {
			allFall.add(f.getLevel().replace("届", ""));
		}
		model.addAttribute("allFall", allFall);
		return "finance/finance_month_report";
	}

	@RequestMapping("getmonth")
	@SystemControllerLog(description = "进入财务月度报表页面")
	public @ResponseBody ResultMessage getmonth(Integer syear) {
		ResultMessage rm = new ResultMessage();
		List<FinanceMonth> fyin = financeYearService.getMonth(syear);
		if((fyin.size() == 1 && fyin.get(0) == null) || fyin.size() == 0){
			rm.setTotal(0);
		}else{
			rm.setTotal(fyin.size());
		}
		rm.setRows(fyin);
		return rm;

	}
}
