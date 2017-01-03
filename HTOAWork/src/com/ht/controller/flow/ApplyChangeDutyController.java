package com.ht.controller.flow;

import java.util.ArrayList;
import java.util.Date;
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
import com.ht.popj.dailyWork.Dutymodel;
import com.ht.popj.dailyWork.Dutymodeldetail;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowBaoxiaodetail;
import com.ht.popj.flow.FlowSupplement;
import com.ht.popj.flow.FlowSupplementDetail;
import com.ht.popj.flow.FlowSwaparrange;
import com.ht.popj.flow.FlowSwaparrangeDetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.FinancePolicquantityset;
import com.ht.service.dailyWork.DutyModelDService;
import com.ht.service.dailyWork.DutymodelService;
import com.ht.service.flow.FlowHolidayService;
import com.ht.service.flow.FlowHolidaydetailService;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowSupplementDetailService;
import com.ht.service.flow.FlowSupplementService;
import com.ht.service.flow.FlowSwaparrangeDetailService;
import com.ht.service.flow.FlowSwaparrangeService;
import com.ht.service.flow.FlowsModelService;
import com.ht.util.DateUtil;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/ApplyChangeDuty")
public class ApplyChangeDutyController {

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
	@Autowired
	DutymodelService dutymodelService;
	@Autowired
	DutyModelDService dutyModelDService;
	@Autowired
	FlowSwaparrangeService flowswaparrangeservice;
	@Autowired
	FlowSwaparrangeDetailService flowswaparrangedetailservice;

	@RequestMapping("/flowApplyChangeDuty")
	@SystemControllerLog(description = "进入员工调班申请页面")
	public String flowHolidayStudList(Model model) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return "login";
		}

		int modelid = dutymodelService.selectIdIsUsing();

		Dutymodeldetail temp = new Dutymodeldetail();
		temp.setModelid(modelid);
		// 值班老师list
		temp.setEmpType(1);
		List<Dutymodeldetail> teacList = dutyModelDService.selectByPJ(temp);
		model.addAttribute("teacList", teacList);
		List<String> weeklist = dutyModelDService.selectWeksBymodelId(modelid);
		model.addAttribute("weeklist", weeklist);

		List<FlowsModeltype> flowmodeltypelist = fmm.selectModelTypeByModelSelId("tbsq");
		model.addAttribute("flowmodeltypelist", flowmodeltypelist);
		return "flow/ApplyChangeDuty";
	}

	// 返回所选类别的所有物品的Json数据
	@RequestMapping("/findteacher")
	@SystemControllerLog(description = "返回所选时间的值班老师")
	public @ResponseBody ResultMessage findmaterial(Model model,Dutymodeldetail  dutymodeldetail) {
		ResultMessage rm = new ResultMessage();
		List<Dutymodeldetail> sList = new ArrayList<Dutymodeldetail>();
		
		int modelid = dutymodelService.selectIdIsUsing();

		Dutymodeldetail temp = new Dutymodeldetail();
		temp.setModelid(modelid);
		temp.setWeekends(dutymodeldetail.getWeekends());
		// 值班老师list
		temp.setEmpType(1);
		sList = dutyModelDService.selectByPJ(temp);

		rm.setTotal(sList.size());
		rm.setRows(sList);
		return rm;
	}

	@RequestMapping("/saveChangeDuty")
	@SystemControllerLog(description = "保存调班单为草稿模式")
	public @ResponseBody int saveFlowHolidayStud(FlowSwaparrange flowsupplement) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return -1;
		}
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		if (null != flowsupplement) {
			flowsupplement.setApprovalstatus(0);
			flowsupplement.setChangeOne(emp.getId());
			flowsupplement.setApplyman(emp.getEmpname());
			Dutymodeldetail dutymodeldetail = dutyModelDService.selectById(flowsupplement.getChangeTwo());
			flowsupplement.setChangeTwoMan(dutymodeldetail.getEmpName());
			flowsupplement.setWeekends(dutymodeldetail.getWeekends());
			flowsupplement.setApprovalstatus(0);// 未提交
			flowsupplement.setId(null);
			int count = flowswaparrangeservice.insertSelective(flowsupplement);
			return count;
		}
		return 0;
	}

	// 修改申请单状态
	@RequestMapping(value = "/updateChangeDutyUpset", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "提交未提交的申请单")
	public @ResponseBody int updateHolidayUpset(FlowSwaparrange flowsupplement) throws Exception {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		if (null != flowsupplement) {
			flowsupplement.setApprovalstatus(1);
			int count = flowswaparrangeservice.updateByPrimaryKeySelective(flowsupplement);// 从未提交状态改为已提交状态
			Dutymodeldetail dutymodeldetail = dutyModelDService.selectById(flowsupplement.getChangeTwo());

			flowsupplement = flowswaparrangeservice.selectByPrimaryKey(flowsupplement.getId());// 获取当前申请单的所有信息
			addHolidayDetial(flowsupplement.getId(), flowsupplement.getFlowmodeltypeid());
			Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
			FlowAll all = new FlowAll();
			all.setFlowmodeltypeid(flowsupplement.getFlowmodeltypeid());
			all.setApplyid(flowsupplement.getId());
			all.setStatus(0);
			all.setEmpid(emp.getId());
			all.setRemark(dep.getDepname() + "的" + emp.getEmpname() + "申请和" 
			+ dutymodeldetail.getEmpName() + "于"+flowsupplement.getWeekends()+"换班,理由是:"
					+ flowsupplement.getRemark() + "");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}

	@RequestMapping("/addChangeDuty")
	@SystemControllerLog(description = "提交申请单")
	public @ResponseBody int addFlowHolidayStud(FlowSwaparrange flowsupplement) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return -1;
		}
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		if (null != flowsupplement) {
			Dutymodeldetail dutymodeldetail = dutyModelDService.selectById(flowsupplement.getChangeTwo());
			flowsupplement.setApplyman(emp.getEmpname());
			flowsupplement.setChangeOne(emp.getId());
			flowsupplement.setWeekends(dutymodeldetail.getWeekends());
			flowsupplement.setApprovalstatus(1);
			int count = flowswaparrangeservice.insertSelective(flowsupplement);
			
			addHolidayDetial(count, flowsupplement.getFlowmodeltypeid());
			Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(flowsupplement.getFlowmodeltypeid());
			all.setStatus(0);
			all.setEmpid(emp.getId());
			all.setRemark(dep.getDepname() + "的" + emp.getEmpname() + "申请和" 
					+ dutymodeldetail.getEmpName() + "于"+flowsupplement.getWeekends()+"换班,理由是:"
							+ flowsupplement.getRemark() + "");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}

	@RequestMapping(value = "/deleteChangeDutyById", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除调班草稿")
	public @ResponseBody int deleteHolidayById(FlowSwaparrange flowsupplement) throws Exception {
		if (null != flowsupplement) {
			int count = flowswaparrangeservice.deleteByPrimaryKey(flowsupplement.getId());
			return count;
		}
		return 0;
	}

	@RequestMapping("/lookChangeDutyDetial")
	@SystemControllerLog(description = "查看审批详情")
	public @ResponseBody ResultMessage lookHolidayDetial(FlowSwaparrange flowsupplement) {
		ResultMessage rm = new ResultMessage();
		List<FlowSwaparrangeDetail> list = new ArrayList<>();
		if (null != flowsupplement) {
			list = flowswaparrangedetailservice.selectBySulementId(flowsupplement.getId());
		}
		rm.setRows(list);
		rm.setTotal(list.size());
		return rm;
	}

	private void addHolidayDetial(int sumentid, int flowMOdelTypeid) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		FlowsModel f = new FlowsModel();
		// FlowApplyMaterial fam = new FlowApplyMaterial();
		f.setFlowmodeltypeid(flowMOdelTypeid);
		List<FlowsModel> fmList = modelService.selectSelective(f);
		for (int i = 0; i < fmList.size(); i++) {
			FlowsModel fm = fmList.get(i);

			FlowSwaparrangeDetail famd = new FlowSwaparrangeDetail();
			famd.setSwaparrangeid(sumentid);
			famd.setEmpid(fm.getEmpid());
			famd.setStatus(0);
			if (fmList.get(i).getRoleid().equals("kong")) {
				famd.setEmpid("");
				famd.setStatus(1);// 通过/完成
			} else if (fmList.get(i).getRoleid().equals("bmld")) {
				Dep dep = depMapper.selectByPrimaryKey(emp.getDepid());
				famd.setEmpid(dep.getChairman());
			} else {
				famd.setEmpid(fmList.get(i).getEmpid());
			}
			if (i == 1) {
				famd.setStatus(2);
			}
			famd.setFlowmodelid(fm.getId());
			famd.setRemark(fm.getRemark());
			flowswaparrangedetailservice.insertSelective(famd);

		}

	}

	@RequestMapping("/flowChangeDutyYes")
	@SystemControllerLog(description = "同意员工调班")
	public @ResponseBody int flowHolidayYes(FlowSwaparrangeDetail holidaydetail) throws Exception {
		if (null != holidaydetail) {
			holidaydetail.setStatus(1);// 审批通过
			int count = flowswaparrangedetailservice.updateByPrimaryKeySelective(holidaydetail);
			// updateHolidaydetial(holidaydetail.getHolidayid());
			FlowSwaparrange fsupplement = flowswaparrangeservice.selectByPrimaryKey(holidaydetail.getSwaparrangeid());
			fsupplement.setApprovalstatus(2);// 1:正在申请;2:通过;-1:不通过
			flowswaparrangeservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(1);
			allMapper.updateByPrimaryKeySelective(all);

			return count;
		}
		// 当student为空时，运行到这里，返回0
		return 0;
	}

	@RequestMapping("/flowChangeDutyNo")
	@SystemControllerLog(description = "不同意员工调班")
	public @ResponseBody int flowHolidayNo(FlowSwaparrangeDetail holidaydetail) throws Exception {
		if (null != holidaydetail) {

			holidaydetail.setStatus(3);// 审批通过
			int count = flowswaparrangedetailservice.updateByPrimaryKeySelective(holidaydetail);
			FlowSwaparrange fsupplement = flowswaparrangeservice.selectByPrimaryKey(holidaydetail.getSwaparrangeid());
			fsupplement.setApprovalstatus(2);// 1:正在申请;2:通过;-1:不通过
			flowswaparrangeservice.updateByPrimaryKeySelective(fsupplement);
			FlowAll all = new FlowAll();
			all.setApplyid(fsupplement.getId());
			all.setFlowmodeltypeid(fsupplement.getFlowmodeltypeid());
			all = allMapper.selectBytypeid(all);
			all.setStatus(2);
			allMapper.updateByPrimaryKeySelective(all);

			return count;
		}
		// 当student为空时，运行到这里，返回0
		return 0;
	}
}
