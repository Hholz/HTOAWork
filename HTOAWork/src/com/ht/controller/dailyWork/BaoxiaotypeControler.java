package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.Baoxiaotype;
import com.ht.service.dailyWork.BaoxiaotypeService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork/Baoxiaotype")
public class BaoxiaotypeControler {
	@Autowired
	BaoxiaotypeService baoxiaotypeservice;
	
	@RequestMapping("/baoxiaotypelist")
	@SystemControllerLog(description = "���뱨��������ҳ��")
	public String seletedepList(Model model){
		return "dailyWork/BaoxiaoType";
	}
	
	//��ѯ�������
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST }) 
	@SystemControllerLog(description = "��ѯ���������Ϣ")
	public @ResponseBody ResultMessage empList(@PathVariable("id") String id,Integer limit, Integer offset,Baoxiaotype baoxiaotype) { 
		ResultMessage rm = new ResultMessage();
		List<Baoxiaotype> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = baoxiaotypeservice.Baoxiaotypelist(baoxiaotype);
        PageInfo<Baoxiaotype> pageInfo = new PageInfo<Baoxiaotype>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("���б������" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm; 
	}
	
	//�����������
	@RequestMapping(value = "/add") 
	@SystemControllerLog(description = "�����������")
	public @ResponseBody int addemp(Baoxiaotype baoxiaotype) {
		baoxiaotype.setCreateTime(new Date());
		baoxiaotype.setUpdateTime(new Date());
		int count=baoxiaotypeservice.insert(baoxiaotype);
		return count; 
	}
	//�޸ı������
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT }) 
	@SystemControllerLog(description = "�޸ı��������Ϣ")
	public @ResponseBody int updateemp(@PathVariable("id") String id,Baoxiaotype baoxiaotype) { 
		baoxiaotype.setUpdateTime(new Date());
		int count=baoxiaotypeservice.updateByPrimaryKey(baoxiaotype);
		return count; 
	}
	//ɾ���������
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE }) 
	@SystemControllerLog(description = "ɾ�����������Ϣ")
	public @ResponseBody int seleteemp(@PathVariable("id") Integer id) { 
		int count=baoxiaotypeservice.deleteByPrimaryKey(id);
		return count; 
	}
}
