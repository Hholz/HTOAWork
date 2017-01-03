package com.ht.controller.android;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.android.util.ResultMessage;
import com.ht.pojo.AHoliday;
import com.ht.popj.student.StuHoliday;
import com.ht.service.student.StuHolidayService;
import com.ht.util.RandomGet;

@Controller
@RequestMapping("android/askfor")
public class AskForAction {
	@Autowired
	StuHolidayService stuHolidayService;
	
	//ѧ�������ٵ�
	@RequestMapping("/stuHoliday")
	public @ResponseBody ResultMessage stuHoliday(StuHoliday holiday){
		//���ñ��
		String date = new Date().toLocaleString().substring(0, 10);
		date = date.replace("-", "");
		holiday.setSerialnum("XSQJ"+date+RandomGet.getSix());
		holiday.setStatus(0);//0������
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String stdate = sDateFormat.format(new java.util.Date());
		holiday.setStdate(stdate);
		int count = stuHolidayService.insertByPJ(holiday);
		if(count>0){
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("200");
			rm.setResultMessage("ע��ɹ���");
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("ע��ʧ�ܣ�");
			return rm;
		}
	}
	//ѧ�������ٵ�
	@RequestMapping("/stuHoliday/get")
	public @ResponseBody ResultMessage GetHoliday(Integer stuId){
		List<StuHoliday> list = new ArrayList<StuHoliday>();
		list = stuHolidayService.selectByStuId(stuId);
		
		List<AHoliday> holiList = new ArrayList<AHoliday>();
		for(StuHoliday s:list){
			AHoliday ah = new AHoliday();
			ah.setId(s.getId());
			ah.setStuId(s.getStuId());
			ah.setReason(s.getReason());
			ah.setStdate(s.getStdate());
			ah.setStatus(s.getStatus());
			ah.setEndate(s.getEndate());
			holiList.add(ah);
		}
		if(list.size()>0){
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("200");
			rm.setResultMessage("��ѯ�ɹ���");
			rm.setContext(holiList);
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("��ѯʧ�ܣ�");
			return rm;
		}
	}
	@RequestMapping("/stuHoliday/agree")
	public @ResponseBody ResultMessage stuHolidayAgree(Integer id){
		StuHoliday holiday = new StuHoliday();
		holiday.setId(id);
		holiday.setStatus(1);
		int count = stuHolidayService.updateByPJ(holiday);
		if(count>0){
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("200");
			rm.setResultMessage("�ɹ���");
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("ʧ�ܣ�");
			return rm;
		}
	}
	@RequestMapping("/stuHoliday/refust")
	public @ResponseBody ResultMessage stuHolidayRefust(Integer id){
		StuHoliday holiday = new StuHoliday();
		holiday.setId(id);
		holiday.setStatus(2);
		int count = stuHolidayService.updateByPJ(holiday);
		if(count>0){
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("200");
			rm.setResultMessage("�ɹ���");
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("ʧ�ܣ�");
			return rm;
		}
	}
}
