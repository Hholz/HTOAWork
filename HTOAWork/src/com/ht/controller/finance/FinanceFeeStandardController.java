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
import com.ht.popj.finance.FinanceFeestandard;
import com.ht.popj.finance.FinanceType;
import com.ht.popj.student.StuReplyModelD;
import com.ht.service.finance.FinanceFeeStandardService;
import com.ht.service.finance.FinanceTypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("finance/feeStandard")
public class FinanceFeeStandardController {

	@Autowired
	FinanceFeeStandardService feeStandardService;
	
	@Autowired
	FinanceTypeService typeService;
	
	@RequestMapping("/feeStandardIndex")
	@SystemControllerLog(description = "����ѧ�ӷ���ȡ��׼����ҳ��")
	public String feeStandardIndex(Model model){
		return "finance/finance_feeStand";
	}
	@RequestMapping("/addStandard")
	@SystemControllerLog(description = "����ѧ�ӷ���Ϣ")
	public @ResponseBody int  addStandard(Model model, FinanceFeestandard standard){
		//��ȡϵͳ��ǰʱ��
		int count = 0;
		int n = feeStandardService.selectByTypeId(standard.getFeeName());
		if(n > 0){
			return count=203;
		}else{
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String str = sdf.format(date);
			standard.setCreateTime(str);
			count = feeStandardService.insert(standard);
		}
		return count;
	}
	@RequestMapping("/feeList")
	@SystemControllerLog(description = "��ѯѧ�ӷ���Ϣ��(Json)")
	public @ResponseBody ResultMessage feeList(int limit, int offset, Model model,FinanceFeestandard standard){
		ResultMessage rm = new ResultMessage();
		List<FinanceFeestandard> sList = new ArrayList<FinanceFeestandard>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = feeStandardService.selectByDynamic(standard);
		// ȡ��ҳ��Ϣ
		PageInfo<FinanceFeestandard> pageInfo = new PageInfo<FinanceFeestandard>(sList);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸���ѧ�ӷ���Ϣ��")
	public @ResponseBody int updatefee(FinanceFeestandard standard){ 
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = sdf.format(date);
		standard.setUpdateTime(str);
		int count = feeStandardService.updateByPrimaryKey(standard);
		return count;
	}
	
	@RequestMapping(value = "/findFee/{typeId}", method = { RequestMethod.PUT })
	public @ResponseBody float findFee( @PathVariable("typeId")Integer typeId){ 
		FinanceFeestandard fee = feeStandardService.selectByPrimaryTypeId(typeId);
		return fee.getMoney();
	}
	
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ��ѧ�ӷ���Ϣ")
	public @ResponseBody int deletefee(Model model,@PathVariable("id")Integer id){ 
		int count  =  feeStandardService.deleteByPrimaryKey(id);
		return count;
	}
	
	@RequestMapping("/findAll")
	public @ResponseBody List<FinanceFeestandard> getAll(Model model){
		List<FinanceFeestandard> list = new ArrayList<FinanceFeestandard>();
		list = feeStandardService.selectFeeStandardAll();
		return list;
	}
}
