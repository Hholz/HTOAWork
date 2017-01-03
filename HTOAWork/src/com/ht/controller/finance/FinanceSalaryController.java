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
	
	//�����·ݹ�������ҳ��
	@RequestMapping("/financesalarylist")
	@SystemControllerLog(description = "�����·ݹ�������ҳ��")
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
	
	// ��ѯԱ���·ݹ�����Ϣ
	@RequestMapping(value = "/salaryList", method = { RequestMethod.POST })
	@SystemControllerLog(description = "��ѯԱ���·ݹ�����Ϣ")
	public @ResponseBody ResultMessage financesalaryList(Integer limit, Integer offset,SalaryList salary) {
		ResultMessage rm = new ResultMessage();
		List<SalaryList> sList = new ArrayList<SalaryList>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = salaryService.selectByDynamic(salary);
		PageInfo<SalaryList> pageInfo = new PageInfo<SalaryList>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		System.out.println("����Ա���·ݹ�����Ϣ��" + total);
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	// ����Ա���·ݹ���
	@RequestMapping(value = "/add")
	@SystemControllerLog(description = "����Ա���·ݹ���")
	public @ResponseBody int add(Model model) {
		int count=0;
		//��һ��:��Ͽ�������
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		
		//��ȡ��ǰ�µ�һ�죺
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Calendar c = Calendar.getInstance();    
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH,1);//����Ϊ1��,��ǰ���ڼ�Ϊ���µ�һ�� 
		String first = format.format(c.getTime())+" 00:00:00";
		System.out.println("===============first:"+first);
		  
		//��ȡ��ǰ�����һ��
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1); 
		calendar.add(Calendar.DATE, -1);
		String last = format.format(calendar.getTime())+" 23:59:59";
		System.out.println("===============last:"+last);
		
		List<BasicSalary> basicInfo = basicSalaryService.selectByDynamic(null);
		List<SalaryList> salArr = new ArrayList<SalaryList>();
		if(basicInfo!=null && basicInfo.size()!=0){
			for(int i=0;i<basicInfo.size();i++){
				//��ѯÿһ��Ա���Ŀ��ڣ����
				Attenstatis att = new Attenstatis();
				att.setEmpname(basicInfo.get(i).getEmpname());
				att.setWorkday(first);
				att.setEndday(last);
				List<Attenstatis> attList = attService.attenrewardsel(att);//��ѯÿһ��Ա���Ŀ���
				SalaryList sal = DetuctSal(attList,basicInfo.get(i).getBasicsalary());;
				sal.setEmpname(attList.get(0).getEmpname());
				sal.setDepid(attList.get(0).getDepid());
				sal.setBasicid(basicInfo.get(i).getId());
				sal.setCreateTime(str);
				sal.setStatus(1);
				//�ۿ�
				salArr.add(sal);
			}
			count = salaryService.insertMuch(salArr);
		}
		
		return count;
	}

	//�ۿ��
	public @ResponseBody SalaryList DetuctSal(List<Attenstatis> attList,float basicMoney){
		SalaryList sal = new SalaryList();
		//�ٵ��ۿ�
		float deduct = 0;
		if(attList.get(0).getLate() != null && attList.get(0).getLate() != 0){
			deduct += aList.get(0).getLatesalary() * attList.get(0).getLate();
		}
		//�����ۿ�
		if(attList.get(0).getAbsent() != null && attList.get(0).getAbsent() !=0){
			deduct += Float.parseFloat(aList.get(0).getWithoutleavesalary()) * attList.get(0).getAbsent(); 
		}
		//��ٿۿ�
		if(attList.get(0).getLeave() != null && attList.get(0).getLeave() !=0){
			Attenstatis a = new Attenstatis();
			a.setAttenstatus(6);
			List<Attenstatis> attsize = attService.Attenstatiselect(a);
			int basicday = attsize.size();
			deduct += (basicMoney/basicday) * attList.get(0).getLeave();
		}
		//�ۿ�
		sal.setDeductmoney(deduct);
		//Ӧ������
		sal.setShouldsalary(basicMoney-deduct);
		//ʵ������
		sal.setFactsalary(basicMoney-deduct);
		//˰ǰ����
		sal.setTaxsalary(basicMoney-deduct);
		return sal;
	}
	
	@RequestMapping(value = "/findemp/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "Ա���·ݹ���--����������--���ݲ�����Ա��")
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
