package com.ht.controller.market;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudent;
import com.ht.service.market.MarketStudentService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/market/allStudent")
public class MarketStudentAllController {

	//�г���������Ԥ��ѧ���ı�service
	@Autowired
	MarketStudentService msService;
	
	@SystemControllerLog(description = "������������ѧ����Ϣҳ��")
	@RequestMapping("/page")
	public String intentionStudentList() {
		return "/market/MarketStudentAll";
	}
	// ����ѧ���б�
	@RequestMapping("/intentionStudentListJson")
	@SystemControllerLog(description = "��������ѧ����json����")
	public @ResponseBody ResultMessage intentionStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (marketStudent != null) {
			if(null!=marketStudent.getMsStatus()){
				if(marketStudent.getMsStatus()==100){
					//ȫ��
					marketStudent.setMsStatus(null);
					list = msService.selectIntentionStudent(marketStudent);
				}else{
					list = msService.selectPredStudentAll(marketStudent);
				}
			}else{
				list = msService.selectIntentionStudent(marketStudent);
			}
		}
		// ȡ��ҳ��Ϣ
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
}
