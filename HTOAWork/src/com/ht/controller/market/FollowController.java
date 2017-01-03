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
@RequestMapping("/market/follow")
public class FollowController {

	@Autowired
	MarketStudentService msService;
	
	@SystemControllerLog(description = "��������ѧ������ҳ��")
	@RequestMapping("/page")
	public String page() {
		return "/market/followStudent";
	}
	@SystemControllerLog(description = "��������ѧ������ҳ��")
	@RequestMapping("/history")
	public String history() {
		return "/market/FollowStuHistory";
	}
	
	// Ҫ���ٵ�����ѧ�����б�
	@RequestMapping("/followStudentListJson")
	@SystemControllerLog(description = "����Ҫ����MarketStudentѧ����json����")
	public @ResponseBody ResultMessage followStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (marketStudent != null) {
			// ȡ����ǰ������ʦ����¼���������Ҫ���ٵ�����ѧ�����б�
			list = msService.selectFollowStudent(marketStudent);
		}
		// ȡ��ҳ��Ϣ
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	// �鿴���ٵ���ʷ��¼
	@RequestMapping("/followStudentListJsonHistory")
	@SystemControllerLog(description = "����Ҫ���и���MarketStudentѧ����json����")
	public @ResponseBody ResultMessage followStudentListJsonHistory(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (marketStudent != null) {
			// ȡ����ǰ������ʦ����¼���������Ҫ���ٵ�����ѧ�����б�
			list = msService.selectAllByPJ(marketStudent);
		}
		// ȡ��ҳ��Ϣ
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

}
