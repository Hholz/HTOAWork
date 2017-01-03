package com.ht.controller.sysSet;

import java.text.ParseException;
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
import com.ht.popj.sysSet.FinanceSalarytypese;
import com.ht.popj.sysSet.StuStatus;
import com.ht.service.sysSet.SalTypeService;
import com.ht.util.RemarkSet;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/sysSet/salType")
public class salTypeSetController {

	@Autowired
	SalTypeService salTypeService;
	@RequestMapping("/page")
	public String page(){  
		return "sysSet/salaryTypeSet";
	}
	/*
	 * ���������json����
	 */
	@RequestMapping("/salTypeListJson")
	public @ResponseBody ResultMessage salTypeListJson(int limit, int offset,FinanceSalarytypese salType){
		ResultMessage rm = new ResultMessage();
		List<FinanceSalarytypese> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = salTypeService.selectAll();
		 // ȡ��ҳ��Ϣ
        PageInfo<FinanceSalarytypese> pageInfo = new PageInfo<FinanceSalarytypese>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*
	 * ��ӹ������
	 */
	@RequestMapping("/addSalType")
	public @ResponseBody int addSalType(FinanceSalarytypese salType) throws ParseException{ 
		//����ʱ��
		salType.setCreateTime(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
		//�����������
		salType.setRemark(RemarkSet.getRemark("���"));
		if(null!=salType){
			int count = salTypeService.insertByPJ(salType);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	/*
	 * �޸Ĺ������
	 */
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateSalType(FinanceSalarytypese salType) throws ParseException{  
		salType.setRemark(RemarkSet.getRemark("����"));
		salType.setUpdateTime(new Date());
		if(null!=salType){
			int count = salTypeService.updateByPJ(salType);
			return count;
		}
		return 0;
	}
	/*
	 * ɾ���������
	 */
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteSalType(Model model,@PathVariable("id")Integer id){  
		int count = salTypeService.delByUpdate(id);
		return count;
	}
}
