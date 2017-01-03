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
	
	//学生添加请假单
	@RequestMapping("/stuHoliday")
	public @ResponseBody ResultMessage stuHoliday(StuHoliday holiday){
		//设置编号
		String date = new Date().toLocaleString().substring(0, 10);
		date = date.replace("-", "");
		holiday.setSerialnum("XSQJ"+date+RandomGet.getSix());
		holiday.setStatus(0);//0待处理
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		String stdate = sDateFormat.format(new java.util.Date());
		holiday.setStdate(stdate);
		int count = stuHolidayService.insertByPJ(holiday);
		if(count>0){
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("200");
			rm.setResultMessage("注册成功！");
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("注册失败！");
			return rm;
		}
	}
	//学生添加请假单
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
			rm.setResultMessage("查询成功！");
			rm.setContext(holiList);
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("查询失败！");
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
			rm.setResultMessage("成功！");
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("失败！");
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
			rm.setResultMessage("成功！");
			return rm;
		}else{
			ResultMessage rm = new ResultMessage();
			rm.setResultCode("201");
			rm.setResultMessage("失败！");
			return rm;
		}
	}
}
