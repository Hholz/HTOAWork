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
import com.ht.popj.education.EduSeminar;
import com.ht.popj.finance.FinanceBalance;
import com.ht.popj.finance.FinanceType;
import com.ht.service.finance.FinanceBalanceService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("finance/financeType")
public class FinanceTypeController {

	@Autowired
	FinanceTypeService financeTypeService;
	
	@Autowired
	FinanceBalanceService financeBalanceService;
	
	@RequestMapping("financeTypeIndex")
	@SystemControllerLog(description = "������֧�����Ϣҳ��")
	public String courseIndex(Model model){
		return "finance/finance_paytype";
	}
	@RequestMapping("paytypeList")
	@SystemControllerLog(description = "��ѯ��֧�����Ϣ��(Json)")
	public @ResponseBody ResultMessage paytypeList(int limit, int offset,Model model,FinanceType type){
		ResultMessage rm = new ResultMessage();
		List<FinanceType> sList = new ArrayList<FinanceType>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = financeTypeService.selectByDynamic(type);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceType> pageInfo = new PageInfo<FinanceType>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addPayType")
	@SystemControllerLog(description = "������һ����֧�����Ϣ")
	public @ResponseBody int addPayType(Model model, FinanceType type){
		//��ȡϵͳ��ǰʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		type.setCreateTime(str);
		int count = financeTypeService.insert(type);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���һ����֧�����Ϣ")
	public @ResponseBody int updatePayType(Model model, FinanceType type){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		type.setUpdateTime(str);
		int count = financeTypeService.updateByPrimaryKeySelective(type);
		return count;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����һ��֧�������Ϣ")
	public @ResponseBody int deletePayType(Model model,@PathVariable("id")Integer id){ 
		int count;
		//���n����0��˵�����ϵ������רҵ������ɾ��
		count =  financeTypeService.deleteByPrimaryKey(id);
		return count;
	}
}
