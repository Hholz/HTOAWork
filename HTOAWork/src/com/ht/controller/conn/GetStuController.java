package com.ht.controller.conn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ht.annotation.SystemControllerLog;
import com.ht.popj.market.MarketStudent;
import com.ht.popj.student.StuReplyModel;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.market.MarketStudentService;
import com.ht.service.student.StuReplyModelService;
import com.ht.service.student.StudentService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

@Controller
@RequestMapping("/conn")
public class GetStuController {

	@Autowired
	StudentService studentService;
	@Autowired
	MarketStudentService msService;
	@Autowired
	StuReplyModelService srmService;
	
	@RequestMapping("/stuListJson")
	public @ResponseBody ResultMessage listJson(int limit, int offset,Student student){
		ResultMessage rm = new ResultMessage();
		List<Student> sList = new ArrayList<>();
		//����ҳ��
		int pageCount = (int)Math.ceil(offset/(limit*1.0));
		PageHelper.startPage(pageCount+1, limit);//��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if(student!=null){
			sList = studentService.selectByDynamic(student);
		}else{
			sList = studentService.selectStudentAll();
		}
		 // ȡ��ҳ��Ϣ
        PageInfo<Student> pageInfo = new PageInfo<Student>(sList);
        long total = pageInfo.getTotal(); //��ȡ�ܼ�¼��
		rm.setTotal((int)total);
		rm.setRows(sList);
		return rm;
	}
		
	// ���ְ�ѧ���б�
	@RequestMapping("/intentStuListJson")
	@SystemControllerLog(description = "������ʽ������ѧ����json����")
	public @ResponseBody ResultMessage intentionStudentListJson(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		marketStudent.setMsStatus(2);//��ʽ������ѧ��
		if (marketStudent != null) { 
			list = msService.selectPredStudentAll(marketStudent);
		}
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	// ���ְ�ѧ���б�
	@RequestMapping("/intentStuListJsonRand")
	@SystemControllerLog(description = "������ʽ������MarketStudent��json����")
	public @ResponseBody ResultMessage intentStuListJsonRand(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		list = msService.selectPredStudentAllRand();
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	// ����ѧѧ���б�
	@RequestMapping("/testStuListJson")
	@SystemControllerLog(description = "������ѧ״̬��MarketStudent��json����")
	public @ResponseBody ResultMessage testStuListJsonRand(int limit, int offset, MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		list = msService.selectTestStudentAll();
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal();
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}
	//ͨ�����id����ȡ���а༶
	@RequestMapping(value = "/stuBySrmId/{id}")
	public @ResponseBody List<Student> stuBySrmId(@PathVariable("id")Integer id) throws Exception{  
		List<Student> stuList = new ArrayList<Student>();
		//�Ȳ�ͨ��srmId��༶
		StuReplyModel srm = srmService.selectById(id);
		int clsId = 0;
		if(null!=srm.getClsId()){
			clsId = srm.getClsId();
		}
		//ͨ���༶id��student
		Student stuTemp = new Student();
		stuTemp.setClassid(clsId);
		stuList = studentService.selectByDynamic(stuTemp);
		return stuList;
	}
	//ͨ���༶id����ȡ���а༶
	@RequestMapping(value = "/stuByClsId/{id}")
	public @ResponseBody List<Student> stuByClsId(@PathVariable("id")Integer id) throws Exception{  
		List<Student> stuList = new ArrayList<Student>();
		//ͨ���༶id��student
		Student stuTemp = new Student();
		stuTemp.setClassid(id);
		stuList = studentService.selectByDynamic(stuTemp);
		return stuList;
	}
}
