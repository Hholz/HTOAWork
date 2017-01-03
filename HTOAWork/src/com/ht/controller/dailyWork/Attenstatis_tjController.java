package com.ht.controller.dailyWork;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.mapper.flow.FlowHolidayMapper;
import com.ht.popj.dailyWork.Attendance_tal;
import com.ht.popj.dailyWork.Attenstatis;
import com.ht.popj.flow.FlowHoliday;
import com.ht.service.dailyWork.Attendance_talService;
import com.ht.service.dailyWork.AttenstatisService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/dailyWork")
public class Attenstatis_tjController {
	@Autowired
	AttenstatisService attenstatisService;
	@Autowired
	Attendance_talService attendance_talService;
	@Autowired
	FlowHolidayMapper holidayMapper;
	
	@RequestMapping("/holidayList")
	public @ResponseBody ResultMessage holidayList(int limit, int offset,Model model,int id) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<FlowHoliday> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		sList = holidayMapper.selectMyholidayEmp(id);
		 // ȡ��ҳ��Ϣ
        PageInfo<FlowHoliday> pageInfo = new PageInfo<FlowHoliday>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
        System.out.println("����ѧ����Ϣ��" + total);
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	
	/*@RequestMapping("/findholiday")
	public @ResponseBody ResultMessage findholiday(int limit, int offset) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<FlowHoliday> holiday =  new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		 // ȡ��ҳ��Ϣ
        PageInfo<FlowHoliday> pageInfo = new PageInfo<FlowHoliday>(holiday);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(holiday);
		return rm;
	}*/
	
	@RequestMapping("/addAttenstatis")
	public @ResponseBody int addAttenstatis()throws Exception{
		
		
		List<Attenstatis> sList = attenstatisService.Attenstatiselect(null);
		if(sList.size()>0 && sList!=null){
			return 0;
		}

		List<Attendance_tal> absenteeism = attendance_talService.absenteeism();//����
		List<Attendance_tal> normal = attendance_talService.normalAttendance();//����
		List<Attendance_tal> late = attendance_talService.lateAttendance();//�ٵ�
		List<Attendance_tal> jiejia = attendance_talService.findidAttendance(0);//�ڼ���
		List<Attendance_tal> abnormal = attendance_talService.abnormalAttendance();//�쳣
		
		Attenstatis attenstatis = new Attenstatis();
		try {
			//�ڼ���
			for(int i=0;i<jiejia.size();i++){
				jiejia.get(i).setAttenstatus(7);
			}
			if(jiejia.size()>0 && jiejia!=null){
				attenstatisService.addinsertlist(jiejia);
			}
			
			//����
			for(int j=0;j<normal.size();j++){
				normal.get(j).setAttenstatus(6);
			}
			if(normal.size()>0 && normal!=null){
				attenstatisService.addinsertlist(normal);
			}
			
			//�ٵ�
			for(int k=0;k<late.size();k++){
				late.get(k).setAttenstatus(4);
			}
			if(late.size()>0 && late!=null){
				attenstatisService.addinsertlist(late);
			}
			
			//����
			for(int s=0;s<absenteeism.size();s++){
				absenteeism.get(s).setAbsent(1);
				absenteeism.get(s).setAttenstatus(0);
			}
			if(absenteeism.size()>0 && absenteeism!=null){
				attenstatisService.addinsertlist(absenteeism);
			}
		
			List<Attendance_tal> abnormal2 = new ArrayList<>();
			Attendance_tal atl = new Attendance_tal();
			Attenstatis att = new Attenstatis();
			for(int i=0;i<abnormal.size();i++){
				//attenstatis = new Attenstatis();
				attenstatis.setDepid(abnormal.get(i).getDepid());
				attenstatis.setEmpname(abnormal.get(i).getEmpname());
				attenstatis.setFinger(abnormal.get(i).getFingerprint());
				attenstatis.setWorkday(abnormal.get(i).getWorkday());
				attenstatis.setLate(abnormal.get(i).getLate());
				attenstatis.setAttenstatus(5);
				att = attenstatisService.findattenstatis(attenstatis);
				if(att==null){
					atl.setDepid(attenstatis.getDepid());
					atl.setAttenstatus(5);
					atl.setFingerprint(attenstatis.getFinger());
					atl.setLate(attenstatis.getLate());
					atl.setWorkday(attenstatis.getWorkday());
					atl.setEmpname(attenstatis.getEmpname());
					abnormal2.add(atl);
				}
				
			}
			if(abnormal.size()>0 && abnormal!=null){
				attenstatisService.addinsertlist(abnormal2);
			}
			
			
	} catch (Exception e) {
		e.printStackTrace();
	}
		return 1;
	}
}
