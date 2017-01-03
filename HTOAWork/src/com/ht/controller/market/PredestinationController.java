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
import com.ht.popj.market.MarketStudent;
import com.ht.service.market.MarketStudentService;
import com.ht.util.ResultMessage;
import com.ht.util.UserInfoUtil;

/*
 * Ԥ������ѧ������ҳ��
 */
@Controller
@RequestMapping("/market/predestination")
public class PredestinationController {

	// �г���������Ԥ��ѧ���ı�service
	@Autowired
	MarketStudentService msService;

	@SystemControllerLog(description = "��������ѧ����Ϣҳ��")
	@RequestMapping("/page")
	public String intentionStudentList() {
		return "/market/PredestinationStudent";
	}

	// Ԥ������ѧ�������б�
	@RequestMapping("/predestinationStudentListJson")
	@SystemControllerLog(description = "����MarketStudentѧ����json����")
	public @ResponseBody ResultMessage predestinationStudentListJson(int limit, int offset,
			MarketStudent marketStudent) {
		ResultMessage rm = new ResultMessage();
		List<MarketStudent> list = new ArrayList<>();
		// ����ҳ��
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);// ��ҳ���Ǵ�1��ʼ����ǰҳ����һҳ��ʾ��������
		if (marketStudent != null) {
			//������id
			if(null != UserInfoUtil.getEmp()){
				marketStudent.setEmpId(UserInfoUtil.getEmp().getId());
			}
			if(null!=marketStudent.getMsStatus()){
				if(marketStudent.getMsStatus()==100){
					//ȫ��
					marketStudent.setMsStatus(null);
					list = msService.selectPredStudentAll(marketStudent);
				}else{
					list = msService.selectPredStudentAll(marketStudent);
				}
			}else{
				list = msService.selectPredStudentDefault(marketStudent);
			}
		}
		// ȡ��ҳ��Ϣ
		PageInfo<MarketStudent> pageInfo = new PageInfo<MarketStudent>(list);
		long total = pageInfo.getTotal(); // ��ȡ�ܼ�¼��
		rm.setTotal((int) total);
		rm.setRows(list);
		return rm;
	}

	// ����Ԥ������ѧ����������ѧ�ѵ����֣�
	@RequestMapping("/addPredestinationStudent")
	@SystemControllerLog(description = "����Ԥ������ѧ��")
	public @ResponseBody int addIntentionStudent(MarketStudent marketStudent) {
		marketStudent.setCreateTime(new Date());
		// ��ȡ��ǰ��¼�û�����Ϣ
		if (null != UserInfoUtil.getEmp()) {
			marketStudent.setEmpId(UserInfoUtil.getEmp().getId());
		}
		// Ĭ��״̬��1.Ԥ������
		marketStudent.setMsStatus(1);
		if (marketStudent != null) {
			int count = msService.insertByPJ(marketStudent);
			return count;
		}
		return 0;
	}

	// �޸ı���ѧ����Ϣ
	@RequestMapping(value = "/predestinationStudent/{id}", method = RequestMethod.PUT)
	@SystemControllerLog(description = "�޸ı���ѧ����Ϣ")
	public @ResponseBody int updatePredestinationStudent(MarketStudent marketStudent) {
		marketStudent.setUpdateTime(new Date());
		if (marketStudent != null) {
			int count = msService.updateByPJ(marketStudent);
			return count;
		}
		return 0;
	}
	
	// ɾ������ѧ����Ϣ
	@RequestMapping(value = "/predestinationStudent/{id}", method = RequestMethod.DELETE)
	@SystemControllerLog(description = "ɾ������ѧ����Ϣ")
	public @ResponseBody int delPredestinationStudent(Model model,@PathVariable("id")Integer id) {
		return msService.deleteById(id);
	}
	//����ɾ������ѧ����Ϣ
	@RequestMapping("/delStudents")
	@SystemControllerLog(description = "����ɾ������ѧ����Ϣ")
	public @ResponseBody int delStudents(String ids) {
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.deleteById(id);
		}
		return count;
	}
	// ������Ϊ��ʽ����״̬
	@RequestMapping("/bePrestudent")
	@SystemControllerLog(description = "������Ϊ��ʽѧ�����ȴ��ְ�")
	public @ResponseBody int bePrestudents(String ids) {
		// ���Ǹ���MarketStudent��ѧ��״̬(msStatus) 2=��ʽ����
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.updateMsStatusById(id, 2);;
		}
		return count;
	}
	// ������Ϊ�Ѿ��ְ�״̬
	@RequestMapping("/beIsClass")
	@SystemControllerLog(description = "������Ϊ�Ѿ��ְ�״̬")
	public @ResponseBody int beIsClasses(String ids) {
		// ���Ǹ���MarketStudent��ѧ��״̬(msStatus) 3=�Ѿ��ְ�״̬
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.updateMsStatusById(id, 3);;
		}
		return count;
	}
	// ������Ϊ��У״̬
	@RequestMapping("/beLeave")
	@SystemControllerLog(description = "������Ϊ��У״̬̬")
	public @ResponseBody int beLeaves(String ids) {
		// ���Ǹ���MarketStudent��ѧ��״̬(msStatus) 4=�뿪
		String[] array = ids.split(",");
		int count = 0;
		for(int i=0;i<array.length;i++){
			int id = Integer.parseInt(array[i]);
			count += msService.updateMsStatusById(id, 4);;
		}
		return count;
	}

	
	
	/********************����ķ�������**************************************/
	// ��Ϊ��ʽ����״̬
	@RequestMapping(value = "/bePrestudent/{id}")
	@SystemControllerLog(description = "��Ϊ��ʽѧ�����ȴ��ְ�")
	public @ResponseBody int bePrestudent(@PathVariable("id") Integer id) {
		// ���Ǹ���MarketStudent��ѧ��״̬(msStatus) 2=��ʽ����
		int count = msService.updateMsStatusById(id, 2);
		return count;
	}

	// ��Ϊ�Ѿ��ְ�״̬
	@RequestMapping(value = "/beIsClass/{id}")
	@SystemControllerLog(description = "�ְ����")
	public @ResponseBody int beIsClass(@PathVariable("id") Integer id) {
		// ���Ǹ���MarketStudent��ѧ��״̬(msStatus) 3=�Ѿ��ְ�״̬
		int count = msService.updateMsStatusById(id, 3);
		return count;
	}

	// ��Ϊ��У״̬
	@RequestMapping(value = "/beLeave/{id}")
	@SystemControllerLog(description = "��ѧ�꣬�������뿪")
	public @ResponseBody int beLeave(@PathVariable("id") Integer id) {
		// ���Ǹ���MarketStudent��ѧ��״̬(msStatus) 4=�뿪
		int count = msService.updateMsStatusById(id, 4);
		// ����Ԥ�������������¼
		return count;
	}
}
