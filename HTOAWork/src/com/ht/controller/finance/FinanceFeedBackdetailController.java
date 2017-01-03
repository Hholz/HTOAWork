package com.ht.controller.finance;

import java.util.ArrayList;
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
import com.ht.popj.finance.FinanceFeedbackdetail;
import com.ht.service.finance.FinanceFeedbackdetailService;
import com.ht.util.ResultMessage;


@Controller
@RequestMapping("/finance/financeFeedBackDetail")
public class FinanceFeedBackdetailController {

	@Autowired
	FinanceFeedbackdetailService financeFeedbackdetailService;
	
	@RequestMapping("financeFeedBackDetailJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,FinanceFeedbackdetail feedback){
		ResultMessage rm = new ResultMessage();
		List<FinanceFeedbackdetail> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		
		sList = financeFeedbackdetailService.selectDynamic(feedback);
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<FinanceFeedbackdetail> pageInfo = new PageInfo<FinanceFeedbackdetail>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("���з�����Ŀ��" + total);
       // System.err.println(sList.get(0).getFloorAdmin());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/add")
	public @ResponseBody int  addfinancefeedbackdetail(Model model, FinanceFeedbackdetail finance){
		if(null!=finance){
			
			int count = financeFeedbackdetailService.insert(finance);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//�޸ķ�����ϸ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatefinancefeedback(Model model,FinanceFeedbackdetail finance){  
		if(null!=finance){
			int count = financeFeedbackdetailService.updateByPrimaryKeySelective(finance);
			return count;
		}
		return 0;
	}
	
	//ɾ��ģ�壬
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deletefinance(Model model,@PathVariable("id")Integer id){
		//ʵ�����޸�״̬�������޸ķ���
				int count = financeFeedbackdetailService.deleteByPrimaryKey(id);
				return count;
	}
}
