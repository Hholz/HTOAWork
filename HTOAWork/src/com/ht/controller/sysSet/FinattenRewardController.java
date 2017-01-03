package com.ht.controller.sysSet;

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
import com.ht.popj.student.StudentReward;
import com.ht.popj.sysSet.FinanceAttencerewardset;
import com.ht.service.sysSet.FinattenRewardService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet")
public class FinattenRewardController {
	
	@Autowired
	FinattenRewardService finattenRewardService;
	
	@RequestMapping("/frewardjsp")
	public String FinanatterReward(){
		return "sysSet/FinattenRewardset";
	}

	//����ѧ��
	@RequestMapping("/ftreward/add")
	public @ResponseBody int addStudentreward(Model model,FinanceAttencerewardset student) throws Exception{ 
		if(null!=student){
			if(null==student.getLatesalary()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if(null==student.getRemark()||student.getRemark().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			int i =finattenRewardService.insertSelective(student);
			return i;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	@RequestMapping("/ftrewardset")
	@SystemControllerLog(description = "����ʱ���controller���list��")
	public @ResponseBody ResultMessage saystudentheart(int limit, int offset,Model model,FinanceAttencerewardset student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<FinanceAttencerewardset> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = finattenRewardService.finattenrewardsel(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<FinanceAttencerewardset> pageInfo = new PageInfo<FinanceAttencerewardset>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//�޸�н��
	@RequestMapping(value = "/ftreward/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateStudentreward(Model model,FinanceAttencerewardset student){  
		if(null!=student){
			if(null==student.getLatesalary()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if(null==student.getSickleavesalary()||student.getSickleavesalary().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			finattenRewardService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
		
	//ɾ��н�ʼ�¼
	@RequestMapping(value = "/ftreward/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteStudentreward(Model model,FinanceAttencerewardset student){  
		if(null!=student){
			if(null==student.getId()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			finattenRewardService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
	
}
