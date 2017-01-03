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
import com.ht.mapper.flow.FlowModeltypeMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowBaoxiaodetail;
import com.ht.popj.flow.FlowSupplement;
import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.service.flow.FlowHolidayService;
import com.ht.service.flow.FlowHolidaydetailService;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowSupplementDetailService;
import com.ht.service.flow.FlowSupplementService;
import com.ht.service.flow.FlowsModelService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/ApplySupplement")
public class ApplySupplementController {

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
	@Autowired
	FlowSupplementService flowsupplementservice;
	@Autowired
	FlowSupplementDetailService flowsupplementdetailservice;
	@Autowired
	FlowModeltypeMapper fmm;
	
	
	@RequestMapping("/flowSupplement")
	@SystemControllerLog(description = "����Ա����ǩ����ҳ��")
	public String flowHolidayStudList(Model model) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return "login";
		}
		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("wdksq");
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "flow/ApplySupplement";
	}
	
	@RequestMapping("/saveSupplement")
	@SystemControllerLog(description = "���油ǩ��Ϊ�ݸ�ģʽ")
	public @ResponseBody int saveFlowHolidayStud(FlowSupplement flowsupplement){
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
		if(null != flowsupplement){
			flowsupplement.setEmpid(emp.getId());
			flowsupplement.setApplyman(emp.getEmpname());
			flowsupplement.setApprovalstatus(0);//δ�ύ
			int count = flowsupplementservice.insertSelective(flowsupplement);
			return count;
		}
		return 0;
	}
	
	//�޸����뵥״̬
	@RequestMapping(value = "/updateSupplementUpset", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "�ύδ�ύ�����뵥")
	public @ResponseBody int updateHolidayUpset(FlowSupplement flowsupplement) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		if(null!=flowsupplement){
			flowsupplement.setApprovalstatus(1);
			int count = flowsupplementservice.updateByPrimaryKeySelective(flowsupplement);//��δ�ύ״̬��Ϊ���ύ״̬
			
			flowsupplement = flowsupplementservice.selectByPrimaryKey(flowsupplement.getId());//��ȡ��ǰ���뵥��������Ϣ
			addHolidayDetial(flowsupplement.getId(),flowsupplement.getFlowmodeltypeid());
			Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
			FlowAll all = new FlowAll();
			all.setFlowmodeltypeid(flowsupplement.getFlowmodeltypeid());
			all.setApplyid(flowsupplement.getId());
			all.setStatus(0);
			all.setEmpid(emp.getId());
			all.setRemark(dep.getDepname()+"��"+emp.getEmpname()+"����"
					+flowsupplement.getSutime()+"ʱ��ǩ,����"+flowsupplement.getSuremark()+"");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/addSupplement")
	@SystemControllerLog(description = "�ύ���뵥")
	public @ResponseBody int addFlowHolidayStud(FlowSupplement flowsupplement){
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
		if(null != flowsupplement){
			flowsupplement.setApplyman(emp.getEmpname());
			flowsupplement.setEmpid(emp.getId());
			flowsupplement.setApprovalstatus(1);
			int count = flowsupplementservice.insertSelective(flowsupplement);
			addHolidayDetial(count,flowsupplement.getFlowmodeltypeid());
			Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(flowsupplement.getFlowmodeltypeid());
			all.setStatus(0);
			all.setEmpid(emp.getId());
			all.setRemark(dep.getDepname()+"��"+emp.getEmpname()+"����"
					+flowsupplement.getSutime()+"ʱ��ǩ,����"+flowsupplement.getSuremark()+"");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/deleteSupplementById", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "ɾ����ٲݸ�")
	public @ResponseBody int deleteHolidayById(FlowSupplement flowsupplement) throws Exception{
		if(null!=flowsupplement){
			int count = flowsupplementservice.deleteByPrimaryKey(flowsupplement.getId());
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/lookSupplementDetial")
	@SystemControllerLog(description = "�鿴��������")
	public @ResponseBody ResultMessage lookHolidayDetial(FlowSupplement flowsupplement){
		ResultMessage rm = new ResultMessage();
		List<FlowSupplementDetail> list = new ArrayList<>();
		if (null != flowsupplement) {
			list = flowsupplementdetailservice.selectBySulementId(flowsupplement.getId());
		}
		rm.setRows(list);
		rm.setTotal(list.size());
		return rm;
	}
	
	private void addHolidayDetial(int sumentid,int flowMOdelTypeid){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.��session��ȡ��ShiroUserInfo����
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if(null != userInfo.getEmp()){
			emp = userInfo.getEmp();
		}
		FlowsModel f = new FlowsModel();
		// FlowApplyMaterial fam = new FlowApplyMaterial();
		f.setFlowmodeltypeid(flowMOdelTypeid);
		List<FlowsModel> fmList = modelService.selectSelective(f);
		for (int i = 0; i < fmList.size(); i++) {
			FlowsModel fm = fmList.get(i);

			FlowSupplementDetail famd = new FlowSupplementDetail();
			famd.setSumentid(sumentid);
			famd.setEmpid(fm.getEmpid());
			famd.setStatus(0);
			if(fmList.get(i).getRoleid().equals("kong")){
				famd.setEmpid("");
				famd.setStatus(1);//ͨ��/���
			}else if(fmList.get(i).getRoleid().equals("bmld")){
				Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
				famd.setEmpid(dep.getChairman());
			}else {
				famd.setEmpid(fmList.get(i).getEmpid());
			}
			if(i == 1){
				famd.setStatus(2);
			}
			famd.setFlowmodelid(fm.getId());
			famd.setRemark(fm.getRemark());
			flowsupplementdetailservice.insertSelective(famd);
			
			
		}
		
	}
	
	@RequestMapping("/flowSupplementYes")
	@SystemControllerLog(description = "ͬ��Ա����ǩ")
	public @ResponseBody int flowHolidayYes(FlowSupplementDetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(1);//����ͨ��
			int count = flowsupplementdetailservice.updateByPrimaryKeySelective(holidaydetail);
//			updateHolidaydetial(holidaydetail.getHolidayid());
			FlowSupplement fsupplement = flowsupplementservice.selectByPrimaryKey(holidaydetail.getSumentid());
			fsupplement.setApprovalstatus(2);//1:��������;2:ͨ��;-1:��ͨ��
			flowsupplementservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(1);
			allMapper.updateByPrimaryKeySelective(all);
			
			
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
	
	@RequestMapping("/flowSupplementNo")
	@SystemControllerLog(description = "��ͬ��Ա����ǩ")
	public @ResponseBody int flowHolidayNo(FlowSupplementDetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			
			holidaydetail.setStatus(3);//����ͨ��
			int count = flowsupplementdetailservice.updateByPrimaryKeySelective(holidaydetail);
			FlowSupplement fsupplement = flowsupplementservice.selectByPrimaryKey(holidaydetail.getSumentid());
			fsupplement.setApprovalstatus(2);//1:��������;2:ͨ��;-1:��ͨ��
			flowsupplementservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(2);
			allMapper.updateByPrimaryKeySelective(all);
			
			return count;
		}
		//��studentΪ��ʱ�����е��������0
		return 0;
	}
}
