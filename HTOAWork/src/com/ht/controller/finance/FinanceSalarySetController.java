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
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.finance.BasicSalary;
import com.ht.popj.finance.SalaryDetail;
import com.ht.popj.sysSet.FinanceSalarytypese;
import com.ht.service.dailyWork.DepService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.finance.BasicSalaryService;
import com.ht.service.finance.SalaryDetailService;
import com.ht.service.finance.SalaryListService;
import com.ht.service.sysSet.SalTypeService;
import com.ht.util.ResultMessage;

/*
 * 操作员工基本工资
 */
@Controller
@RequestMapping("/finance/FinanceSalarySet")
public class FinanceSalarySetController {

	@Autowired
	EmpService empService;
	
	@Autowired
	SalaryDetailService salaryDetailService;
	
	@Autowired
	DepService depService;
	
	@Autowired
	SalTypeService salTypeService;
	
	@Autowired
	SalaryListService salaryListService;
	
	@Autowired
	BasicSalaryService basicSalaryService;
	
	@RequestMapping("/FinSalarySetList")
	@SystemControllerLog(description = "进入员工基本工资页面")
	public String List(Model model) throws Exception{
		List<Emp> emplist = empService.selectEmp(null);
		model.addAttribute("emplist", emplist);
		
		List<Dep> deplist = depService.selectDepList();
		model.addAttribute("deplist", deplist);
		return "/finance/salaryList";
	}
	
	@RequestMapping(value = "/findList", method = { RequestMethod.POST })
	@SystemControllerLog(description = "查询工资设置列表json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset,BasicSalary salary) {

		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		List<BasicSalary> salaryList = basicSalaryService.selectByDynamic(salary);
		PageInfo<BasicSalary> pageInfo = new PageInfo<BasicSalary>(salaryList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(salaryList);
		return rm;
	}
	/*
	 * 查询工资类别
	 * */
	@RequestMapping(value = "/salTypeList", method = { RequestMethod.POST })
	@SystemControllerLog(description = "查询工资类别")
	public @ResponseBody ResultMessage salTypeList(Integer limit, Integer offset,SalaryDetail detail) {

		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		List<FinanceSalarytypese> salaryList = salTypeService.selectAll();
		PageInfo<FinanceSalarytypese> pageInfo = new PageInfo<FinanceSalarytypese>(salaryList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(salaryList);
		return rm;
	}
	
	
	@RequestMapping(value = "/add", method = { RequestMethod.POST })
	@SystemControllerLog(description = "新增工资设置数据")
	public @ResponseBody int add(String ids,String salarys,SalaryDetail detail){
		int count=0;
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		detail.setCreateTime(str);
		//将传过来的值转化成数组
		String[] idArr = ids.split(",");
		String[] salaryArr = salarys.split(",");
		//先判断是否是这个部门一起新增
		if(detail.getEmpid()=="" && (detail.getDepid()!=null || detail.getDepid()!=0)){
			Emp emp = new Emp();
			emp.setDepid(detail.getDepid());
			List<Emp> empidList = empService.selectEmp(emp);
			//往工资表添加数据
			float money=0;
			//所有的工资来别相加
			for(int n=0;n<salaryArr.length;n++){
				money += Float.parseFloat(salaryArr[n]);
			}
			for(int m=0;m<empidList.size();m++){
				BasicSalary sal = new BasicSalary();
				sal.setBasicsalary(money);
				sal.setEmpname(empidList.get(m).getEmpname());
				sal.setDepid(empidList.get(m).getDepid());
				sal.setCreateTime(str);
				sal.setStatus(1);
				count = basicSalaryService.insert(sal);//添加数据
				if(sal.getId() != 0){
					ArrayList<SalaryDetail> salArr = new ArrayList<SalaryDetail>();
					for(int i=0;i<idArr.length;i++){
						SalaryDetail det = new SalaryDetail();
						det.setSalaryId(sal.getId());
						det.setSalTypeid(Integer.parseInt(idArr[i]));//工资类别
						det.setMoney(Float.parseFloat(salaryArr[i]));//工资资金
						det.setDepid(empidList.get(m).getDepid());
						det.setEmpid(empidList.get(m).getEmpname());
						det.setStatus(1);
						det.setCreateTime(str);
						salArr.add(det);
					}
					//批量新增
					salaryDetailService.insertMuch(salArr);
				}
			}
		}else{
			//往工资表添加数据
			float money=0;
			//所有的工资来别相加
			for(int n=0;n<salaryArr.length;n++){
				money += Float.parseFloat(salaryArr[n]);
			}
			BasicSalary sal = new BasicSalary();
			sal.setBasicsalary(money);
			sal.setEmpname(detail.getEmpid());
			sal.setDepid(detail.getDepid());
			sal.setCreateTime(str);
			sal.setStatus(1);
			count = basicSalaryService.insert(sal);//添加数据
			if(sal.getId() != 0){
				ArrayList<SalaryDetail> salArr = new ArrayList<SalaryDetail>();
				for(int i=0;i<idArr.length;i++){
					SalaryDetail det = new SalaryDetail();
					det.setSalaryId(sal.getId());
					det.setSalTypeid(Integer.parseInt(idArr[i]));//工资类别
					det.setMoney(Float.parseFloat(salaryArr[i]));//工资资金
					det.setDepid(detail.getDepid());
					det.setEmpid(detail.getEmpid());
					det.setStatus(1);
					salArr.add(det);
				}
				//批量新增
				salaryDetailService.insertMuch(salArr);
			}
		}
		return count;
	}
	
	@RequestMapping(value = "/findEmp/{depid}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "查询员工信息")
	public @ResponseBody ResultMessage findEmp(@PathVariable("depid")Integer depid){
		ResultMessage result = new ResultMessage();
		Emp emp = new Emp();
		emp.setDepid(depid);
		List<Emp> emplist = empService.selectEmp(emp);
		result.setRows(emplist);
		result.setTotal(emplist.size());
		return result;
	}
	
	@RequestMapping(value = "/detailList/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "查询基本工资明细")
	public @ResponseBody ResultMessage detailList(Integer limit, Integer offset,@PathVariable("id")Integer id) {

		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		
		SalaryDetail det = new SalaryDetail();
		det.setSalaryId(id);
		List<SalaryDetail> salaryList = salaryDetailService.selectByDynamic(det);
		PageInfo<SalaryDetail> pageInfo = new PageInfo<SalaryDetail>(salaryList);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(salaryList);
		return rm;
	}
	
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除工资设置信息")
	public @ResponseBody int delete(@PathVariable("id")Integer id){
		int count=0;
		if(id!=null || id!=0){
			//删除工资设置表
			count = basicSalaryService.deleteByPrimaryKey(id);
			//删除工资明细表
			count = salaryDetailService.deleteBySalaryId(id);
		}
		return count;
	}
	
}