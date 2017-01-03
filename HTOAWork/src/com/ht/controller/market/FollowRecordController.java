package com.ht.controller.market;

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
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.market.MarketFollow;
import com.ht.popj.market.TrackStudent;
import com.ht.service.market.MarketFollowService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/market/followrecord")
public class FollowRecordController {
	@Autowired
	MarketFollowService marketfollowService;
	
	@RequestMapping(value = "/page/{id}")
	@SystemControllerLog(description = "ͨ��ѧ��id����ѧ��������Ϣҳ��")
	public String page(Model model,@PathVariable("id") Integer id){
		model.addAttribute("stuId", id);
		return "market/FollowRecordStudent";
	}
	
	//����ѧ���б�
	@RequestMapping("/marketrecordListJson")
	@SystemControllerLog(description = "����ѧ�����ٱ�json����")
	public @ResponseBody ResultMessage trackStudentListJson(int limit, int offset,MarketFollow marketfllow){
		ResultMessage rm = new ResultMessage();
		List<MarketFollow> list = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(marketfllow != null){
			list = marketfollowService.findfllowList1(marketfllow);
		}else{
			list = marketfollowService.findfllowList2();
		}
		// ȡ��ҳ��Ϣ
        PageInfo<MarketFollow> pageInfo = new PageInfo<MarketFollow>(list);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(list);
		return rm;
	}
	
	//�����������
	@RequestMapping("/addMarketfllow")
	@SystemControllerLog(description = "����ѧ������")
	public @ResponseBody int addMarketfllow(MarketFollow marketfllow){
		marketfllow.setCreateTime(new Date().toLocaleString());
		marketfllow.setTracktime(new Date().toLocaleString());
		if(marketfllow != null){
			int count = marketfollowService.addfllow(marketfllow);
			return count;
		}
		return 0;
	}
	
	//�޸ĸ���ѧ��
	@RequestMapping(value="/marketrecord/{id}",method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸�ѧ������")
	public @ResponseBody int updateTrackStudent(MarketFollow marketfllow){
		marketfllow.setUpdateTime(new Date().toLocaleString());
		marketfllow.setTracktime(new Date().toLocaleString());
		if(marketfllow != null){
			int count = marketfollowService.updatefllow(marketfllow);
			return count;
		}
		return 0;
	}
	
	//ɾ������ѧ��
	@RequestMapping(value="/marketrecord/{id}",method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ��ѧ������")
	public @ResponseBody int deleteTrackStudent(@PathVariable("id") Integer id){
		int count = marketfollowService.deletefllow(id);
		return count;
	}
}
