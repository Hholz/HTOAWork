package com.ht.controller.market;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudentCount;
import com.ht.popj.student.StudentFall;
import com.ht.service.market.MarketStudentService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;
@Controller
@RequestMapping("/market/report")
public class MarketReportController {
    @Autowired
    MarketStudentService marketStudentService;
    @Autowired
	StudentInfoService studentInfoService;
	
	@SystemControllerLog(description = "��������ѧ���¶ȱ���ҳ��")
	@RequestMapping("/mouthPage")
	public String mouthPage(Model model) throws Exception {
		List<StudentFall> fall = studentInfoService.selectStudentFall();
		List allFall = new ArrayList();
		for (StudentFall f : fall) {
			allFall.add(f.getLevel().replace("��", ""));
		}
		model.addAttribute("allFall", allFall);
		return "/market/Market_mouth_report";
	}
	@SystemControllerLog(description = "��������ѧ����ȶȱ���ҳ��")
	@RequestMapping("/yearPage")
	public String yearPage(Model model) throws Exception {
		List<StudentFall> fall = studentInfoService.selectStudentFall();
		List allFall = new ArrayList();
		for (StudentFall f : fall) {
			allFall.add(f.getLevel().replace("��", ""));
		}
		model.addAttribute("allFall", allFall);
		return "/market/Market_year_report";
	}
	/*
	 * һ���12���±�������
	 */
	@RequestMapping("/getmonth")
	public @ResponseBody ResultMessage getmonth(Integer syear) {
		ResultMessage rm = new ResultMessage();
		MarketStudentCount imsc = marketStudentService.countIntenByYear(syear);//����
		MarketStudentCount smsc = marketStudentService.countByYearStatus(3, syear);//�ɹ�����
		MarketStudentCount tmsc = marketStudentService.countTestByYear(syear);//����ѧѧ��
		MarketStudentCount lmsc = marketStudentService.countByYearStatus(4, syear);//����Уѧ��
		if(imsc==null&&smsc==null&&tmsc==null&&lmsc==null){
			rm.setTotal(0);
		}else{
			rm.setTotal(1);
		}
		Map map = new HashMap();
		map.put("imsc", imsc);
		map.put("smsc", smsc);
		map.put("tmsc", tmsc);
		map.put("lmsc", lmsc);
		rm.setRows(map);
		return rm;
	}
	/*
	 * ��ݱ�������
	 */
	@RequestMapping("/getyear")
	public @ResponseBody ResultMessage getyear(int startyear, int endyear) {
		ResultMessage rm = new ResultMessage();
		List<Integer> ilist = new ArrayList<Integer>();
		List<Integer> slist = new ArrayList<Integer>();
		List<Integer> tlist = new ArrayList<Integer>();
		List<Integer> llist = new ArrayList<Integer>();
		List<Integer> dataList = new ArrayList<Integer>();
		for(int year = startyear;year<=endyear;year++){
			ilist.add(marketStudentService.reportIntenYearByYear(year));
			slist.add(marketStudentService.reportYearByYearStatus(3, year));//�ѷְ�
			tlist.add(marketStudentService.reportTestYearByYear(year));
			llist.add(marketStudentService.reportYearByYearStatus(4, year));//����У
			dataList.add(year);
		}
		if(ilist==null&&slist==null&&tlist==null&&llist==null){
			rm.setTotal(0);
		}else{
			rm.setTotal(1);
		}
		Map map = new HashMap();
		map.put("ilist", ilist);
		map.put("slist", slist);
		map.put("tlist", tlist);
		map.put("llist", llist);
		map.put("dataList", dataList);
		rm.setRows(map);
		return rm;

	}
}
