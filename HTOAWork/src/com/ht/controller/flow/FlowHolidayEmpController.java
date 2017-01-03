package com.ht.controller.flow;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowHoliday;
import com.ht.popj.flow.FlowHolidaydetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.service.flow.FlowHolidayService;
import com.ht.service.flow.FlowHolidaydetailService;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowsModelService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowHolidayEmp")
public class FlowHolidayEmpController {

	@Autowired
	FlowHolidayService holidayService;
	@Autowired
	FlowModeltypeService modelTypeService;
	@Autowired
	FlowsModelService modelService;
	@Autowired
	StudentClassMapper studentClassMapper;
	@Autowired
	DepMapper depMapper;
	@Autowired
	FlowHolidaydetailService holidaydetailService;
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	FlowAllMapper allMapper;
	
	@RequestMapping("/flowHolidayEmpList")
	@SystemControllerLog(description = "����Ա����ٵ�����ҳ��")
	public String flowHolidayStudList(Model model) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return "login";
		}
		List<FlowsModeltype> modeltypes = modelTypeService.selectModelTypeByModelSelId("ygqj");
		model.addAttribute("modeltypes", modeltypes);
		return "flow/flowHolidayEmp";
	}
	
	@RequestMapping("/saveFlowHolidayEmp")
	@SystemControllerLog(description = "������ٵ�Ϊ�ݸ�ģʽ")
	public @ResponseBody int saveFlowHolidayStud(FlowHoliday holiday){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return -1;
		}
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		if(null != holiday){
			holiday.setEmpid(emp.getId());
			holiday.setUpset(0);
			holiday.setApplyman(emp.getEmpname());
			int count = holidayService.insertEmpHoliday(holiday);
			return count;
		}
		return 0;
	}
	
	//�޸����뵥״̬
	@RequestMapping(value = "/updateHolidayUpset", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�ύδ�ύ�����뵥")
	public @ResponseBody int updateHolidayUpset(FlowHoliday flowHoliday) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		if(null!=flowHoliday){
			flowHoliday.setUpset(1);
			int count = holidayService.updateByPrimaryKeySelective(flowHoliday);//��δ�ύ״̬��Ϊ���ύ״̬
			
			flowHoliday = holidayService.selectByPrimaryKey(flowHoliday.getId());//��ȡ��ǰ���뵥��������Ϣ
			addHolidayDetial(flowHoliday.getId(),flowHoliday.getFlowmodeltypeid());
			Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
			FlowAll all = new FlowAll();
			all.setApplyid(flowHoliday.getId());
			all.setFlowmodeltypeid(flowHoliday.getFlowmodeltypeid());
			all.setStudid(flowHoliday.getStudid());
			all.setStatus(0);
			all.setEmpid(emp.getId());
			all.setRemark(dep.getDepname()+"��"+emp.getEmpname()+"��"
					+flowHoliday.getStrattime()+"��"+flowHoliday.getRemark()+"�����������");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/addFlowHolidayEmp")
	@SystemControllerLog(description = "�ύ��ٵ�")
	public @ResponseBody int addFlowHolidayStud(FlowHoliday holiday){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return -1;
		}
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		if(null != holiday){
			holiday.setApplyman(emp.getEmpname());
			holiday.setEmpid(emp.getId());
			holiday.setUpset(1);
			int count = holidayService.insertEmpHoliday(holiday);
			addHolidayDetial(count,holiday.getFlowmodeltypeid());
			Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(holiday.getFlowmodeltypeid());
			all.setStudid(holiday.getStudid());
			all.setStatus(0);
			all.setEmpid(emp.getId());
			all.setRemark(dep.getDepname()+"��"+emp.getEmpname()+"��"
					+holiday.getStrattime()+"��"+holiday.getRemark()+"�����������");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/deleteHolidayById", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����ٲݸ�")
	public @ResponseBody int deleteHolidayById(FlowHoliday flowHoliday) throws Exception{
		if(null!=flowHoliday){
			int count = holidayService.deleteByPrimaryKey(flowHoliday.getId());
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/lookHolidayDetial")
	@SystemControllerLog(description = "�鿴��������")
	public @ResponseBody ResultMessage lookHolidayDetial(FlowHoliday holiday){
		ResultMessage rm = new ResultMessage();
		List<FlowHolidaydetail> list = new ArrayList<>();
		if (null != holiday) {
			list = holidaydetailService.selectByHolidayid(holiday.getId());
		}
		rm.setRows(list);
		rm.setTotal(list.size());
		return rm;
	}
	
	private void addHolidayDetial(int holidayid,int flowMOdelTypeid){
		FlowsModel flowsModel = new FlowsModel();
		flowsModel.setFlowmodeltypeid(flowMOdelTypeid);
		List<FlowsModel> flowsModels = modelService.selectSelective(flowsModel);//��ȡģ�������ڵ��е�����
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		//�������뵥�������ڵ�
		for (int i = 0; i < flowsModels.size(); i++) {
			FlowHolidaydetail holidaydetail = new FlowHolidaydetail();
			holidaydetail.setHolidayid(holidayid);
			holidaydetail.setFlowmodelid(flowsModels.get(i).getId());
			holidaydetail.setStatus(0);//δ����
			if(flowsModels.get(i).getRoleid().equals("kong")){
				holidaydetail.setEmpid("");
				holidaydetail.setStatus(1);//ͨ��/���
			}else if(flowsModels.get(i).getRoleid().equals("bmld")){
				Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
				holidaydetail.setEmpid(dep.getChairman());
			}else {
				holidaydetail.setEmpid(flowsModels.get(i).getEmpid());
			}
			
			holidaydetailService.insertSelective(holidaydetail);
		}
		
		updateHolidaydetial(holidayid);
	}
	
	//�޸������ڵ�״̬
	private void updateHolidaydetial(int holidayid){
		//�޸�
		List<FlowHolidaydetail> holidaydetails = holidaydetailService.selectAll(holidayid);
		if(holidaydetails.size()>0){
			FlowHolidaydetail holidaydetail = holidaydetails.get(0);
			holidaydetail.setStatus(2);//������
			holidaydetailService.updateByPrimaryKeySelective(holidaydetail);
		}else{
			FlowHoliday flowHoliday = new FlowHoliday();
			flowHoliday.setId(holidayid);
			flowHoliday.setUpset(3);//����ͨ��
			holidayService.updateByPrimaryKeySelective(flowHoliday);
			flowHoliday = holidayService.selectByPrimaryKey(holidayid);
			FlowAll all = new FlowAll();
			all.setApplyid(holidayid);
			all.setFlowmodeltypeid(flowHoliday.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(1);
			allMapper.updateFlowAllStatus(all);
		}
	}
	
	@RequestMapping("/flowEmpHolidayYes")
	@SystemControllerLog(description = "ͬ��Ա�����")
	public @ResponseBody int flowHolidayYes(FlowHolidaydetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(1);//����ͨ��
			int count = holidaydetailService.updateByPrimaryKeySelective(holidaydetail);
			updateHolidaydetial(holidaydetail.getHolidayid());
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	@RequestMapping("/flowEmpHolidayNo")
	@SystemControllerLog(description = "��ͬ��Ա�����")
	public @ResponseBody int flowHolidayNo(FlowHolidaydetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(3);//��ͬ������
			int count = holidaydetailService.updateByPrimaryKeySelective(holidaydetail);
			
			FlowHoliday flowHoliday = new FlowHoliday();
			flowHoliday.setId(holidaydetail.getHolidayid());
			flowHoliday.setUpset(2);//����ʧ��,���뵥��Ч
			holidayService.updateByPrimaryKeySelective(flowHoliday);
			flowHoliday = holidayService.selectByPrimaryKey(holidaydetail.getHolidayid());
			FlowAll all = new FlowAll();
			all.setApplyid(holidaydetail.getHolidayid());
			all.setFlowmodeltypeid(flowHoliday.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
}
