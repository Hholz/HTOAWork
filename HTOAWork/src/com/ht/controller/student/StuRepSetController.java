package com.ht.controller.student;


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
import com.ht.popj.dailyWork.Noticetype;
import com.ht.popj.finance.FinanceFeestandard;
import com.ht.popj.student.StuRepSet;
import com.ht.service.student.StuRepSetService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/student/StuRepSet")
public class StuRepSetController {
	@Autowired
	StuRepSetService sturepsetService;
	
	@RequestMapping("/sturepsetList")
	@SystemControllerLog(description = "������ģ����Ϣҳ��")
	public String noticeList(Model model){
		return "/student/StuRepSet";
	}
	
	@RequestMapping("/sturepsetListJson")
	@SystemControllerLog(description = "���ش��ģ���json����")
	public @ResponseBody ResultMessage noticeTypeListJson(int limit, int offset,StuRepSet sturepset){
		ResultMessage rm = new ResultMessage();
		List<StuRepSet> list = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		
		list = sturepsetService.selectStuRepSet(sturepset);
		
		 // ȡ��ҳ��Ϣ
        PageInfo<StuRepSet> pageInfo = new PageInfo<StuRepSet>(list);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	
	@RequestMapping("/addStuRepSet")
	@SystemControllerLog(description = "�������ģ��")
	public @ResponseBody int addStuRepSet(StuRepSet sturepset){
		sturepset.setCreateTime(new Date());
		if(sturepset != null){
			int count = sturepsetService.insertStuRepSet(sturepset);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value="/stuRepSet/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸Ĵ��ģ��")
	public @ResponseBody int updateStuRepSet(StuRepSet sturepset){
		sturepset.setUpdateTime(new Date());
		if(sturepset != null){
			int count = sturepsetService.updateStuRepSet(sturepset);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value="/stuRepSet/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ�����ģ��")
	public @ResponseBody int deleteStuRepSet(@PathVariable("id") Integer id){
		int count = sturepsetService.deleteStuRepSet(id);
		return count;
	}
	@RequestMapping("/findAll")
	public @ResponseBody List<StuRepSet> getAll(Model model){
		List<StuRepSet> list = new ArrayList<StuRepSet>();
		list = sturepsetService.selectStuRepSet(new StuRepSet());
		return list;
	}
}
