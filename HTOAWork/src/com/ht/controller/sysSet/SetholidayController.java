package com.ht.controller.sysSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.ht.popj.student.StudentSayface;
import com.ht.popj.sysSet.SysHoliday;
import com.ht.service.sysSet.SetholidayService;
import com.ht.util.ResultMessage;
import com.ht.util.Utildemo;

@Controller
@RequestMapping("/sysSet")
public class SetholidayController {

	@Autowired
	SetholidayService setholidayService;
	
	//����ڼ����趨����
	@RequestMapping("/Setholiday")
	public String setholiday(Model model,SysHoliday student){
		System.out.println("==00=");
		List<SysHoliday> sList = setholidayService.setholidayselect(student);
		if(sList!=null&&sList.size()>0){
			String holiday = sList.get(0).getHolidaytime();
			String[] hd = holiday.split(" ");
			String hdd = hd[0];
			String[] hddy = hdd.split("-");
			String hy = hddy[0];//��ȡ���
			String hm = hddy[1];//��ȡ�·�
			int hmi = Integer.parseInt(hm);
		
			Date date = new Date();
			SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
			String cn = ds.format(date);	
			String[] ti = cn.split("-");
			String y = ti[0];//��ȡ���
			String m = ti[1];//��ȡ�·�
			int mi = Integer.parseInt(m)-1;
			if(m.equals(01)){
				mi = 12;
			}
			
			int md =monthday();
			if((sList.size()==md)&&(hmi==mi)&&(hy.equals(y))){
				int day = 1;
				model.addAttribute("day", day);
			}else {
				int day = 2;
				model.addAttribute("day", day);
			}
			
		}else{
			int day = 3;
			model.addAttribute("day", day);
		}
		
		
		return "/sysSet/Setholiday";
	}
	
	@RequestMapping("/holidaylist")
	@SystemControllerLog(description = "�ڼ���ʱ������controller���list��")
	public @ResponseBody ResultMessage holidaylist(int limit, int offset,Model model,SysHoliday student) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<SysHoliday> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = setholidayService.setholidayselect(student);
		}
		System.out.println(sList.size());
		 // ȡ��ҳ��Ϣ
        PageInfo<SysHoliday> pageInfo = new PageInfo<SysHoliday>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	//ȡ�õ����ж�����
	public int monthday(){
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);
		String[] ti = cn.split("-");
		String y = ti[0];//��ȡ���
		String m = ti[1];//��ȡ�·�
		Calendar cal = Calendar.getInstance();//����calendar������ȡʱ������
		cal.set(Calendar.YEAR,Integer.parseInt(y));//������
		cal.set(Calendar.MONTH,Integer.parseInt((m))-2);//Java�·ݲ�0��ʼ��-HH:ss:mm
		int dm = cal.getActualMaximum(Calendar.DATE);
		return dm;
	}
	
	@RequestMapping("/updatelist")
	public @ResponseBody int updatelist(SysHoliday systime){
		List<SysHoliday> sList = setholidayService.setholidayselect(systime);
		for(int i=0;i<sList.size();i++){
			sList.get(i).setStatus(0);
			setholidayService.updateByPrimaryKeySelective(sList.get(i));
		}
		
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);
		String[] ti = cn.split("-");
		String y = ti[0];//��ȡ���
		String m = ti[1];//��ȡ�·�
		Calendar cal = Calendar.getInstance();//����calendar������ȡʱ������
		cal.set(Calendar.YEAR,Integer.parseInt(y));//������
		cal.set(Calendar.MONTH,Integer.parseInt((m))-2);//Java�·ݲ�0��ʼ��-HH:ss:mm
		int dm = cal.getActualMaximum(Calendar.DATE);
		System.out.println("====="+dm);//�µ�����
		for(int i=0;i<dm;i++){
			int month = Integer.parseInt((m))-1;//ǰһ�� 
			if(month==0){
				y=2016+"";
				month = 12;
			}
			int day = i+1;
			String datetimes = y+"-"+month+"-"+day;
			systime.setHolidaytime(datetimes);
			Utildemo ut = new Utildemo();//����Utildemo�����������������
			String xiqi = ut.getDayOfWeekByDate(datetimes);
			if(xiqi.equals("������")){
				systime.setHolidaystat(0);
			}else{
				systime.setHolidaystat(1);
			}
			systime.setRemark("���ޣ�����");
			setholidayService.insertSelective(systime);
		}
		
		return 1;
	}
	
	//�����ڼ���ʱ������
	@RequestMapping("/addtime")
	public @ResponseBody int addtime(Model model,SysHoliday systime) throws Exception{ 
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);
		String[] ti = cn.split("-");
		String y = ti[0];//��ȡ���
		String m = ti[1];//��ȡ�·�
		Calendar cal = Calendar.getInstance();//����calendar������ȡʱ������
		cal.set(Calendar.YEAR,Integer.parseInt(y));//������
		cal.set(Calendar.MONTH,Integer.parseInt((m))-2);//Java�·ݲ�0��ʼ��-HH:ss:mm
		int dm = cal.getActualMaximum(Calendar.DATE);
		System.out.println("====="+dm);//�µ�����
		for(int i=0;i<dm;i++){
			int month = Integer.parseInt((m))-1;//ǰһ�� 
			if(month==0){
				y=2016+"";
				month = 12;
			}
			int day = i+1;
			String datetimes = y+"-"+month+"-"+day;
			systime.setHolidaytime(datetimes);
			Utildemo ut = new Utildemo();//����Utildemo�����������������
			String xiqi = ut.getDayOfWeekByDate(datetimes);
			if(xiqi.equals("������")){
				systime.setHolidaystat(0);
			}else{
				systime.setHolidaystat(1);
			}
			systime.setRemark("���ޣ�����");
			setholidayService.insertSelective(systime);
		}
		return 1;
	}
	
	@RequestMapping(value = "/update/{id}", method = { RequestMethod.PUT })
	public @ResponseBody int updateholiday(Model model,SysHoliday systime){  
		if(systime!=null){
			
			setholidayService.updateByPrimaryKeySelective(systime);
			return 1;
		}
		return 0;
	}
	
	//ɾ��ѧ��
	@RequestMapping(value = "/delete/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody int deleteholiday(Model model,SysHoliday systime){  
		if(systime!=null){
			setholidayService.updateByPrimaryKeySelective(systime);
			return 1;
		}
		return 0;
	}
}
