package com.ht.controller.dailyWork;

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
import com.ht.popj.dailyWork.DutyMaxTemp;
import com.ht.popj.dailyWork.Dutymodel;
import com.ht.popj.dailyWork.Dutymodeldetail;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.student.StudentFall;
import com.ht.service.dailyWork.DutyModelDService;
import com.ht.service.dailyWork.DutymodelService;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.student.StudentInfoService;
import com.ht.service.sysSet.FlowscheduleService;
import com.ht.util.DateUtil;
import com.ht.util.ResultMessage;


@Controller
@RequestMapping("/dailyWork/DutyModel")
public class DutyModelController {

	@Autowired
	private EmpService empService;
	@Autowired
	private DutymodelService dutymodelService;
	@Autowired
	private DutyModelDService dutyModelDService;
	@Autowired
	FlowscheduleService fscheduleservice;
	
	@Autowired
	StudentInfoService studentInfoService;
	// ----------------------------------ֵ��ģ��-------------------------------
	// ֵ��ģ���б�
	@RequestMapping("/dutymodelList")
	@SystemControllerLog(description = "����ֵ��ģ�����ϸ��Ϣҳ��")
	public String dutymodelList(Model model) throws Exception {
		List<Emp> list = new ArrayList<>();
		Emp emp = new Emp();
		list = empService.selectEmp(emp);
		model.addAttribute("emp", list);

		List<Dutymodel> list2 = new ArrayList<>();
		list2 = dutymodelService.findDutymodelList2();
		
		List<StudentFall> fallList = studentInfoService.selectStudentFall();//���
		model.addAttribute("model", list2);
		model.addAttribute("fallList", fallList);
		return "/dailyWork/Dutymodel";
	}
	//����鿴����ֵ��
	@RequestMapping("/dutyWeek")
	public String dutyWeek(Model model) throws Exception {
		int modelid = dutymodelService.selectIdIsUsing();//��ѯ��ǰ�����õ�ģ��id
		//��ѯ��ģ�壬ĳ��ֵ�������ͣ�����ж��ٸ�
		List<DutyMaxTemp> dmtList = dutyModelDService.selectDutyMaxBymodelId(modelid);
		List<Dutymodeldetail> modelDList = dutyModelDService.selectByDutymodelId(modelid);//��ѯģ����ϸ
		List<String> weksList = dutyModelDService.selectWeksBymodelId(modelid);//�����ж����ܼ�
		model.addAttribute("dmtList", dmtList);
		model.addAttribute("modelDList", modelDList);
		model.addAttribute("weksList", weksList);
		model.addAttribute("weekDays", DateUtil.getWeekOfDate(new Date()));//�������ܼ�
		return "/dailyWork/DutyWeek";
	}

	@RequestMapping("/dutymodelListJson")
	@SystemControllerLog(description = "����ֵ��ģ���json����")
	public @ResponseBody ResultMessage dutymodelListJson(int limit, int offset, Dutymodel dutymodel) {
		ResultMessage rm = new ResultMessage();
		List<Dutymodel> list = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (dutymodel != null) {
			list = dutymodelService.findDutymodelList1(dutymodel);
		} else {
			list = dutymodelService.findDutymodelList2();
		}
		// ȡ��ҳ��Ϣ
		PageInfo<Dutymodel> pageInfo = new PageInfo<Dutymodel>(list);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

	// ����ֵ��ģ��
	@RequestMapping("/addDutymodel")
	@SystemControllerLog(description = "����ֵ��ģ��")
	public @ResponseBody int addDutymodel(Dutymodel dutymodel) {
		dutymodel.setCreateTime(new Date());
		if (dutymodel != null) {
			int count = dutymodelService.addDutymodel(dutymodel);
			return count;
		}
		return 0;
	}

	// �޸�ֵ��ģ��
	@RequestMapping(value = "/dutymodel/{id}", method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸�ֵ��ģ��")
	public @ResponseBody int updateDutymodel(Dutymodel dutymodel) {
		dutymodel.setUpdateTime(new Date());
		if (dutymodel != null) {
			int count = dutymodelService.updateDutymodel(dutymodel);
			return count;
		}
		return 0;
	}

	// ɾ��ֵ��ģ��
	@RequestMapping(value = "/dutymodel/{id}", method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ��ֵ��ģ��")
	public @ResponseBody int deleteDutymodel(@PathVariable("id") Integer id) {
		int count = dutymodelService.deleteDutymodel(id);
		return count;
	}

	// ------------------------ֵ��ģ����ϸ----------------------
	// ֵ��ģ����ϸ�б�
	@RequestMapping("/modeldetailListJson")
	@SystemControllerLog(description = "����ֵ��ģ����ϸ��json����")
	public @ResponseBody ResultMessage modeldetailListJson(int limit, int offset, Dutymodeldetail modeldetail) {
		ResultMessage rm = new ResultMessage();
		List<Dutymodeldetail> list = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (modeldetail != null) {
			list = dutyModelDService.selectAll();
		}
		// ȡ��ҳ��Ϣ
		PageInfo<Dutymodeldetail> pageInfo = new PageInfo<Dutymodeldetail>(list);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

	// ����ֵ��ģ����ϸ
	@RequestMapping("/addModeldetail")
	@SystemControllerLog(description = "����ֵ��ģ����ϸ")
	public @ResponseBody int addModeldetail(Dutymodeldetail modeldetail) {
		modeldetail.setCreateTime(new Date());
		if (modeldetail != null) {
			int count = dutyModelDService.insertByPJ(modeldetail);
			return count;
		}
		return 0;
	}

	// �޸�ֵ��ģ����ϸ
	@RequestMapping(value = "/modeldetail/{id}", method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸�ֵ��ģ����ϸ")
	public @ResponseBody int updateModeldetail(Dutymodeldetail modeldetail) {
		modeldetail.setUpdateTime(new Date());
		if (modeldetail != null) {
			int count = dutyModelDService.updateByPJ(modeldetail);
			return count;
		}
		return 0;
	}

	// ɾ��ֵ��ģ����ϸ
	@RequestMapping(value = "/modeldetail/{id}", method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ��ֵ��ģ����ϸ")
	public @ResponseBody int deleteModeldetail(@PathVariable("id") Integer id) {
		int count = dutyModelDService.deleteById(id);
		return count;
	}
	
	// ���ø�ģ��
	@RequestMapping("/using/{id}")
	@SystemControllerLog(description = "������ǰֵ��ģ��")
	public @ResponseBody int using(@PathVariable("id") Integer id) {
		int count = dutymodelService.usingById(id);
		return count;
	}
	
}
