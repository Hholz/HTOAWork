package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.HolidayType;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.service.dailyWork.HolidaytypeService;
import com.ht.service.dailyWork.MaterialTypeService;
import com.ht.util.ResultMessage;
/*
 * �����������
 */
@Controller
@RequestMapping("/dailyWork/Holidaytype")
public class HolidayTypeController {
	@Autowired
	HolidaytypeService htservice;
	
	//����������ҳ��
	@RequestMapping("/HtList")
	@SystemControllerLog(description = "����������ҳ��")
	public String studentList() {
		return "/dailyWork/HolidayTypeList";
	}
	
	//�����������
	@RequestMapping("/add")
	@SystemControllerLog(description = "�����������")
	public @ResponseBody int addMaterilType(Model model, HolidayType holidaytype) {
		int a = htservice.insertSelective(holidaytype);

		return a;
	}
	
	//���ؼ�������JSON����
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "���ؼ�������JSON����")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			HolidayType holidaytype) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		if(holidaytype.getHolidaytypename() == null){
			holidaytype.setHolidaytypename("");
		}
		List<HolidayType>holidaytypelist = htservice.selectByName(holidaytype);
		PageInfo<HolidayType> pageInfo = new PageInfo<HolidayType>(holidaytypelist);
		long total = pageInfo.getTotal(); 
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(holidaytypelist);
		return rm;
	}
	
	//�޸ļ��������Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�޸ļ��������Ϣ")
	public @ResponseBody int updataMaterilType(Model model, HolidayType holidaytype) {
		htservice.updateByPrimaryKeySelective(holidaytype);
		
		return 1;
	}
	
	//ɾ�����������Ϣ
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ�����������Ϣ")
	public @ResponseBody int deleteMaterilType(Model model, Integer id) {
		htservice.deleteByPrimaryKey(id);
		return 1;
	}
}
