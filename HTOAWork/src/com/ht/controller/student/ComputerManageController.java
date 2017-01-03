package com.ht.controller.student;

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
import com.ht.popj.student.ComputerManage;
import com.ht.popj.student.StudentSayface;
import com.ht.service.student.ComputerManageService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student")
public class ComputerManageController {
	
	@Autowired
	ComputerManageService computerManageService;
	
	@RequestMapping("/cmanagejsp")
	public String computermanagejsp(){
		return "student/ComputermajorList";
	}

	@RequestMapping("/cmanagelist")
	@SystemControllerLog(description = "���Թ���controller���list��")
	public @ResponseBody ResultMessage computermanageList(int limit, int offset,Model model,ComputerManage student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<ComputerManage> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = computerManageService.computermanage(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<ComputerManage> pageInfo = new PageInfo<ComputerManage>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//�������Թ������
	@RequestMapping("/cmanage/add")
	public @ResponseBody int addComputermanage(Model model,ComputerManage student) throws Exception{ 
		if(null!=student){
			if(null==student.getManagetype()||student.getManagetype().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			int i =computerManageService.insertSelective(student);
			return i;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	//���µ��Թ������
	@RequestMapping(value = "/cmanage/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateComputermanage(Model model,ComputerManage student){  
		if(null!=student){
			if(null==student.getManagetype()||student.getManagetype().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			if(null==student.getRemark()||student.getRemark().isEmpty()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			computerManageService.updateByPrimaryKeySelective(student);
			System.out.println("pppp000000000ooo");
			return 1;
		}
		return 0;
	}
	
	//ɾ�����Թ�·���
	@RequestMapping(value = "/cmanage/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteComputermanage(Model model,ComputerManage student){  
		if(null!=student){
			if(null==student.getId()){
				//����0����Demo.jsp��ajax�ص������������ж��Ƿ������ɹ�
				return 0;
			}
			computerManageService.updateByPrimaryKeySelective(student);
			return 1;
		}
		return 0;
	}
}
