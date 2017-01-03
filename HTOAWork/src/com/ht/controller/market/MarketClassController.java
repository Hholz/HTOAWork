package com.ht.controller.market;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudent;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.market.MarketStudentService;
import com.ht.service.student.StudentInfoService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

import common.Logger;

/*
 * һ����ʦ����ѧ�����
 * �������Σ��༶�쵼����������
 */
@Controller
@RequestMapping("/market/marketClass")
public class MarketClassController {
	Logger logger = Logger.getLogger(MarketClassController.class);
	
	@Autowired
	StudentInfoService studentInfoService;
	@Autowired
	MarketStudentService marketStudentService;
	@Autowired
	MarketStudentService msService;
	@RequestMapping("/page")
	public String intentionStudentList(Model model) throws Exception {
		List<StudentFall> sList = studentInfoService.selectStudentFall();
		model.addAttribute("sList", sList);
		return "/market/marketClass";
	}
	@RequestMapping("/testClassListJson")
	@SystemControllerLog(description = "��ѧ�༶����controller���list��")
	public @ResponseBody ResultMessage classlistJson(int limit, int offset,Model model,StudentClass studentClass) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<StudentClass> sList = new ArrayList<>();
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);
		//��Ӧ�����˵Ĺ���İ༶
		if(null != UserInfoUtil.getEmp()){
			studentClass.setClteacherId(UserInfoUtil.getEmp().getId());
			studentClass.setLeaderId(UserInfoUtil.getEmp().getId());
			sList = studentInfoService.selectTestCls(studentClass);//��ѧ�༶
		}
        PageInfo<StudentClass> pageInfo = new PageInfo<StudentClass>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
	//�ְ����
	@RequestMapping("/stuclassallot")
	public @ResponseBody int Studentclassallot(Integer classid,String ids){
		StudentClass cls = studentInfoService.selectById(classid);
		int count = 0;
		String[] array = ids.split(",");
		for(int i=0;i<array.length;i++){
			//�жϰ༶�����Ƿ�����                /*����������ˣ��Ƿ��ع�������������*/
			if(cls.getCount()<=cls.getCountstu()){
				logger.error("�༶��������");
				count = -1;
				break;
			}
			String stid = array[i];
			//ͨ��ѧ�����id��ȡ��ʽ����ѧ������Ϣ
			MarketStudent ms = marketStudentService.selectById(Integer.parseInt(stid));
			ms.setClassid(classid);
			count += marketStudentService.updateByPJ(ms);
		}
		return count;
	}
	//��ѯ�ð��ж���ѧ��
	@RequestMapping("/getCountByclsId")
	public @ResponseBody int getCountByclsId(Integer classid){
		return marketStudentService.countByclsId(classid);
	}
	
	@RequestMapping("/marketStudentByclsIdListJson")
	public @ResponseBody ResultMessage marketStudentByclsIdListJson(MarketStudent marketStudent) throws Exception{
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = msService.selectIntentionStudent(marketStudent);
		rm.setRows(list);
		return rm;
	}
}
