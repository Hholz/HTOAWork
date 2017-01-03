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
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.finance.FinaceFeedbackset;
import com.ht.service.finance.FinanceFeedbackdetailService;
import com.ht.service.finance.FinanceFeedbacksetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/finance/FeedBackSet")

public class FinanceFeedBackSetControllor {

	@Autowired FinanceFeedbacksetService financefeedbacksetService;
	@Autowired FinanceFeedbackdetailService financeFeedbackdetailService;
	@RequestMapping("/feedback")
	@SystemControllerLog(description = "���뷴��ģ��ҳ��")
	public String feedback(Model model) throws Exception{
		List<FinaceFeedbackset> financeFeedBackList = financefeedbacksetService.selectFinanceFeedbacksetAll();
		model.addAttribute("financeFeedBackList", financeFeedBackList);
		return "/finance/finance_FeedBackSet";
	}
	@RequestMapping("financefeedbacklistJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Model model,FinaceFeedbackset feedback){
		ResultMessage rm = new ResultMessage();
		List<FinaceFeedbackset> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(feedback.getId()!=null){
			sList = financefeedbacksetService.selectFinanceFeedbacksetByDynamic(feedback);
		}else{
			sList = financefeedbacksetService.selectFinanceFeedbacksetAll();
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<FinaceFeedbackset> pageInfo = new PageInfo<FinaceFeedbackset>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("���з�����Ŀ��" + total);
       // System.err.println(sList.get(0).getFloorAdmin());
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	@RequestMapping("/addfinancefeedback")
	public @ResponseBody int  addfinancefeedback(Model model, FinaceFeedbackset finance){
		if(null!=finance){
			if(null==finance.getName()||finance.getName().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			
			int count = financefeedbacksetService.insert(finance);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//�޸ķ���ģ��
		@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
		public @ResponseBody int updatefinancefeedback(Model model,FinaceFeedbackset finance){  
			if(null!=finance){
				if(null==finance.getName()||finance.getName().isEmpty()){
					//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
					return 0;
				}
				
				int count = financefeedbacksetService.updateByPrimaryKeySelective(finance);
				return count;
			}
			return 0;
		}
		
		//ɾ��ģ�壬
		@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
		public @ResponseBody int delete(Model model,@PathVariable("id")Integer id){
			//ʵ�����޸�״̬�������޸ķ���
			int count = financefeedbacksetService.deleteByPrimaryKey(id);
			return count;
		}
		
}
