package com.ht.controller.sysSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.ht.popj.sysSet.FinanceAttenceset;
import com.ht.service.sysSet.FinanattenService;
import com.ht.util.ResultMessage;


@Controller
@RequestMapping("/sysSet")
public class FinattsetController {
	
	@Autowired 
	FinanattenService finanattenservice;
	
	@RequestMapping("fattenjsp")
	public String finanattenset(){
		System.out.println("=====");
		return "sysSet/Financeattenceset";
	}

	@RequestMapping("/fattensel")
	@SystemControllerLog(description = "����ʱ��controller���list��")
	public @ResponseBody ResultMessage finanattensel(int limit, int offset,Model model,FinanceAttenceset student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<FinanceAttenceset> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = finanattenservice.financeattensel(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<FinanceAttenceset> pageInfo = new PageInfo<FinanceAttenceset>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//�����ϰ�ʱ��
	@RequestMapping("/fatten/add")
	public @ResponseBody int addfinanceattenset(Model model,FinanceAttenceset student) throws Exception{ 
		if(null!=student){
			String[] t1 = student.getTime1().split(" ");
			String time1 = t1[1];
			String[] t2 = student.getTime2().split(" ");
			String time2 = t2[1];
			String[] t3 = student.getTime3().split(" ");
			String time3 = t3[1];

			student.setTime1(time1);
			student.setTime2(time2);
			student.setTime3(time3);
			
			
			int i =finanattenservice.insertSelective(student);
			return i;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//�޸�ʱ��
	@RequestMapping(value = "/fatten/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatefinanceattenset(Model model,FinanceAttenceset student){  
		if(null!=student){
			if(null==student.getTime1()||student.getTime1().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if(null==student.getTime2()||student.getTime2().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if(null==student.getTime3()||student.getTime3().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			String[] t1 = student.getTime1().split(" ");
			String time1 = t1[1];
			String[] t2 = student.getTime2().split(" ");
			String time2 = t2[1];
			String[] t3 = student.getTime3().split(" ");
			String time3 = t3[1];
			student.setTime1(time1);
			student.setTime2(time2);
			student.setTime3(time3);
			
			finanattenservice.updateByPrimaryKeySelective(student);
			System.out.println("p==========p");
			return 1;
		}
		return 0;
	}
	
	@RequestMapping(value = "/qiyong/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updatefinanceattenstat(Model model,FinanceAttenceset student){
		int ids = student.getId();
		List<FinanceAttenceset> sList = finanattenservice.selectidnull(ids);
		for(int i=0;i<sList.size();i++){
			int id = sList.get(i).getId();
			FinanceAttenceset upstat = new FinanceAttenceset();
			upstat.setId(id);
			upstat.setStatus(0);
			finanattenservice.updateByPrimaryKeySelective(upstat);
		}
		finanattenservice.updateByPrimaryKeySelective(student);
		return 1;
	}
	
	//ɾ��ѧ��
	@RequestMapping(value = "/fatten/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deletefinanceattenset(Model model,FinanceAttenceset student){  
		if(null!=student){
			if(null==student.getId()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			finanattenservice.deleteByPrimaryKey(student.getId());
			return 1;
		}
		return 0;
	}
}
