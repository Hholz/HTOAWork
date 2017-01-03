package com.ht.controller.finance;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.finance.FinanceType;
import com.ht.service.finance.FinanceIncomeAndExpenseService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("finance/financeIncomeAndExpense")
public class FinanceIncomeAndExpenseController {

	@Autowired
	FinanceIncomeAndExpenseService financeIncomeAndExpenseService;

	@Autowired
	FinanceTypeService financeTypeService;

	@RequestMapping("/list")
	public String list(Model model) {
		List<FinanceType> allType = financeTypeService.selectPayTypeAll();
		model.addAttribute("allType", allType);
		return "/finance/finance_report_print";
	}

	@RequestMapping("/balancelist")
	@SystemControllerLog(description = "���ط�����֪ͨ�б�json����")
	public @ResponseBody ResultMessage list(int limit, int offset, FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = new ArrayList<FinanceBalance>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeIncomeAndExpenseService.selectFinanceBalance(balance);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceBalance> pageInfo = new PageInfo<FinanceBalance>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping(value = "/getSum", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "����֧������json����")
	public @ResponseBody ResultMessage getSum(FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = financeIncomeAndExpenseService.getIncomeAndExpenseSum(balance);
		if ((sList.size() == 1 && sList.get(0) == null) || sList.size() == 0) {
			FinanceBalance b = new FinanceBalance();
			b.setAllin(0.0);
			b.setAllout(0.0);
			sList.add(0, b);
		}
		if(sList.get(0).getAllin()==null){
			sList.get(0).setAllin(0.0);
		}
		if(sList.get(0).getAllout()==null){
			sList.get(0).setAllout(0.0);
		}
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	@RequestMapping(value = "/getSumIn", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "��������json����")
	public @ResponseBody ResultMessage getSumIn(FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> sList = financeIncomeAndExpenseService.getTypeSumIn(balance);
		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/getSumOut", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "����֧��json����")
	public @ResponseBody ResultMessage getSumOut(FinanceBalance balance) {
		ResultMessage rm = new ResultMessage();
		List<FinanceBalance> inList = financeIncomeAndExpenseService.getTypeSumOut(balance);
		rm.setTotal(inList.size());
		rm.setRows(inList);
		return rm;
	}
}
