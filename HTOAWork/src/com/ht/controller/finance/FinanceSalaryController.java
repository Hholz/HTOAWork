package com.ht.controller.finance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.ht.popj.dailyWork.Attenstatis;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.finance.BasicSalary;
import com.ht.popj.finance.SalaryList;
import com.ht.popj.sysSet.FinanceAttencerewardset;
import com.ht.service.dailyWork.AttendancecountService;
import com.ht.service.dailyWork.AttenstatisService;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.finance.BasicSalaryService;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.finance.SalaryListService;
import com.ht.service.sysSet.FinattenRewardService;
import com.ht.service.sysSet.SalTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/financesalary")
public class FinanceSalaryController {
	
	@Autowired
	SalaryListService salaryService;
	
	@Autowired
	FinanceBalanceService balanceService;
	
	@Autowired
	AttenstatisService attService;
	
	@Autowired
	EmpService empservice;
	
	@Autowired
	DepService depservice;
	
	@Autowired
	FinattenRewardService finattenRewardService;
	
	@Autowired
	BasicSalaryService basicSalaryService;
	
	private List<Emp> emplist=new ArrayList<>();
	
	private List<FinanceAttencerewardset> aList = new ArrayList<>();
	
	//进入月份工资生成页面
	@RequestMapping("/financesalarylist")
	@SystemControllerLog(description = "进入月份工资生成页面")
	public String seletefinancesalaryList(Model model){
		emplist=empservice.selectEmp(null);
		aList = finattenRewardService.finattenrewardsel(null);
		List<Dep> deplist =depservice.selectDepList();
		List<FinanceAttencerewardset> list=finattenRewardService.finattenrewardsel(new FinanceAttencerewardset());
		model.addAttribute("emp", emplist);
		model.addAttribute("dep", deplist);
		model.addAttribute("list", list);
		return "finance/FinanceSalary";
	}
	
	// 查询员工月份工资信息
	@RequestMapping(value = "/salaryList", method = { RequestMethod.POST })
	@SystemControllerLog(description = "查询员工月份工资信息")
	public @ResponseBody ResultMessage financesalaryList(Integer limit, Integer offset,SalaryList salary) {
		ResultMessage rm = new ResultMessage();
		List<SalaryList> sList = new ArrayList<SalaryList>();
		// 计算页数
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// 该页数是从1开始（当前页数，一页显示的条数）
		sList = salaryService.selectByDynamic(salary);
		PageInfo<SalaryList> pageInfo = new PageInfo<SalaryList>(sList);
		long total = pageInfo.getTotal(); // 获取总记录数
		System.out.println("共有员工月份工资信息：" + total);
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	// 新增员工月份工资
	@RequestMapping(value = "/add")
	@SystemControllerLog(description = "新增员工月份工资")
	public @ResponseBody int add(Model model) {
		int count=0;
		//第一步:结合考情数据
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		
		//获取当前月第一天：
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
		String first = format.format(c.getTime())+" 00:00:00";
		System.out.println("===============first:"+first);
		  
		//获取当前月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		String last = format.format(calendar.getTime())+" 23:59:59";
		System.out.println("===============last:"+last);
		
		List<BasicSalary> basicInfo = basicSalaryService.selectByDynamic(null);
		List<SalaryList> salArr = new ArrayList<SalaryList>();
		if(basicInfo!=null && basicInfo.size()!=0){
			for(int i=0;i<basicInfo.size();i++){
				//查询每一个员工的考勤，情况
				Attenstatis att = new Attenstatis();
				att.setEmpname(basicInfo.get(i).getEmpname());
				att.setWorkday(first);
				att.setEndday(last);
				List<Attenstatis> attList = attService.attenrewardsel(att);//查询每一个员工的考情
				SalaryList sal = DetuctSal(attList,basicInfo.get(i).getBasicsalary());;
				sal.setEmpname(attList.get(0).getEmpname());
				sal.setDepid(attList.get(0).getDepid());
				sal.setBasicid(basicInfo.get(i).getId());
				sal.setCreateTime(str);
				sal.setStatus(1);
				//扣款
				salArr.add(sal);
			}
			count = salaryService.insertMuch(salArr);
		}
		
		return count;
	}

	//扣款方法
	public @ResponseBody SalaryList DetuctSal(List<Attenstatis> attList,float basicMoney){
		SalaryList sal = new SalaryList();
		//迟到扣款
		float deduct = 0;
		if(attList.get(0).getLate() != null && attList.get(0).getLate() != 0){
			deduct += aList.get(0).getLatesalary() * attList.get(0).getLate();
		}
		//旷工扣款
		if(attList.get(0).getAbsent() != null && attList.get(0).getAbsent() !=0){
			deduct += Float.parseFloat(aList.get(0).getWithoutleavesalary()) * attList.get(0).getAbsent(); 
		}
		//请假扣款
		if(attList.get(0).getLeave() != null && attList.get(0).getLeave() !=0){
			Attenstatis a = new Attenstatis();
			a.setAttenstatus(6);
			List<Attenstatis> attsize = attService.Attenstatiselect(a);
			int basicday = attsize.size();
			deduct += (basicMoney/basicday) * attList.get(0).getLeave();
		}
		//扣款
		sal.setDeductmoney(deduct);
		//应发工资
		sal.setShouldsalary(basicMoney-deduct);
		//实发工资
		sal.setFactsalary(basicMoney-deduct);
		//税前工资
		sal.setTaxsalary(basicMoney-deduct);
		return sal;
	}
	
	@RequestMapping(value = "/findemp/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "员工月份工资--下拉框联动--根据部门找员工")
	public @ResponseBody ResultMessage seleteemp(@PathVariable("id") Integer id) {
		ResultMessage rm = new ResultMessage();
		Emp emp=new Emp();
		emp.setDepid(id);
		List<Emp> emplist=empservice.selectEmp(emp);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}
	
	@RequestMapping(value = "/findreward/{id}", method = { RequestMethod.POST })
	public @ResponseBody ResultMessage seletereward(@PathVariable("id") Integer id) {
		ResultMessage rm = new ResultMessage();
		FinanceAttencerewardset f=new FinanceAttencerewardset();
		f.setId(id);
		List<FinanceAttencerewardset> flist=finattenRewardService.finattenrewardsel(f);
		rm.setTotal(flist.size());
		rm.setRows(flist);
		return rm;
	}
	
}
