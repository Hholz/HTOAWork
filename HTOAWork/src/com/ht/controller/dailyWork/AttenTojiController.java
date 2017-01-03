package com.ht.controller.dailyWork;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.popj.dailyWork.Attenstatis;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.service.dailyWork.AttenstatisService;
import com.ht.service.dailyWork.AttentojiService;
import com.ht.service.dailyWork.EmpService;

@Controller
@RequestMapping("/dailyWork")
public class AttenTojiController {
	
	@Autowired
	EmpService empservice;
	@Autowired
	AttenstatisService attenstatisService;
	@Autowired
	AttentojiService attentojiService;
	@Autowired
	DepMapper depmapper;
	
	@RequestMapping("/attenTojijsp")
	public String attentojijsp(Model model,Emp emp,Attenstatis student){
		List<Emp> empList = empservice.selectEmp(emp);
		List<Dep> deplist = depmapper.selectDepList();
		int empsize = empList.size();
		List<Attenstatis> attenList = attenstatisService.Attenstatiselandtoji(student);
		int attensize = attenList.size();
		int aam = 0;
		String ati = "";
		if(attenList!=null && attenList.size()>0){
			ati = attenList.get(0).getWorkday();
			String ay = ati.substring(0,4);
			String am = ati.substring(5,7);
			aam = Integer.parseInt(am);
			
		}
		
		Date date = new Date();
		SimpleDateFormat ds = new SimpleDateFormat("yyyy-MM-dd");
		String cn = ds.format(date);	
		String[] ti = cn.split("-");
		String y = ti[0];//��ȡ���
		String m = ti[1];//��ȡ�·�
		int mi = Integer.parseInt(m)-1;
		
		
		if(empsize==attensize && (aam==mi)){//��Ա������ȣ������·���ͬ����1
			int size = 1;
			model.addAttribute("sizes", size);
			model.addAttribute("ati", ati);
		}
		if(empsize!=attensize || (aam!=mi)){//���淴֮
			int size = 2;
			model.addAttribute("sizes", size);
			
		}
		
		
		
		model.addAttribute("deplist", deplist);
		model.addAttribute("empList", empList);
		return "/dailyWork/AttenToji";
	}
	
//	@RequestMapping("/attenTojiadd")
//	public @ResponseBody int attenTojiadd(Emp emp,Attentoji atten,Attenstatis statis) throws Exception{
//		List<Emp> empList = empservice.selectEmp(emp);
//		for(int i=0;i<empList.size();i++){
//			int finger = empList.get(i).getFingerprint();
//			statis.setFinger(finger);
//			Attenstatis	stat = attenstatisService.Attenstatiselandtoji(statis);
//			atten.setDepid(stat.getDepid());
//			atten.setAbsents((stat.getAbsent()).intValue());
//			atten.setEmpname(stat.getEmpname());
//			atten.setLates(stat.getLate());
//			atten.setLeavecs(stat.getLeave().intValue());
//			atten.setWorktime(stat.getWorkday());
//			atten.setFinger(finger);
//			attentojiService.insertSelective(atten);
//		}
//		
//		return 1;
//	}
	
//	//��ʾ���ӱ����Ϣlist
//	@RequestMapping("/AttenTojiList")
//	public @ResponseBody ResultMessage Attenstatiszblist(int limit, int offset,Model model,Attentoji student) throws Exception{
//		ResultMessage rm = new ResultMessage();
//		List<Attentoji> sList = new ArrayList<>();
//		//����ҳ��
//		int pageCount = (int)Math.ceil(offset/(limit*1.0));
//		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
//		if(student!=null){
//			sList = attentojiService.Attentojiselect(student);
//		}
//		System.out.println(sList.size());
//		 // ȡ��ҳ��Ϣ
//        PageInfo<Attentoji> pageInfo = new PageInfo<Attentoji>(sList);
//        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
//        System.out.println("����ѧ����Ϣ��" + total);
//		rm.setTotal((int)total);
//		rm.setRows(sList);
//		return rm;
//	}
	
	
}
