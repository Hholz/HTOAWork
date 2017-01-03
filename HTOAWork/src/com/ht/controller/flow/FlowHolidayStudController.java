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
@RequestMapping("/flow/flowHolidayStud")
public class FlowHolidayStudController {

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
	
	@RequestMapping("/flowHolidayStudList")
	@SystemControllerLog(description = "进入学生请假单申请页面")
	public String flowHolidayStudList(Model model) throws Exception{
		List<FlowsModeltype> modeltypes = modelTypeService.selectModelTypeByModelSelId("xsqj");
		model.addAttribute("modeltypes", modeltypes);
		return "flow/flowHolidayStud";
	}
	
	@RequestMapping("/saveFlowHolidayStud")
	@SystemControllerLog(description = "保存请假单为草稿模式")
	public @ResponseBody int saveFlowHolidayStud(FlowHoliday holiday){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return -1;
		}
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null != holiday){
			holiday.setStudid(student.getId());
			holiday.setUpset(0);
			int count = holidayService.insertSelective(holiday);
			return count;
		}
		return 0;
	}
	
	//修改申请单状态
	@RequestMapping(value = "/updateHolidayUpset", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "提交未提交的申请单")
	public @ResponseBody int updateHolidayUpset(FlowHoliday flowHoliday) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null!=flowHoliday){
			flowHoliday.setUpset(1);
			int count = holidayService.updateByPrimaryKeySelective(flowHoliday);//从未提交状态改为已提交状态
			
			flowHoliday = holidayService.selectByPrimaryKey(flowHoliday.getId());//获取当前申请单的所有信息
			
			addHolidayDetial(student.getClassid(),flowHoliday.getId(),flowHoliday.getFlowmodeltypeid());//调用新增审批节点的方法
			
			StudentClass studentClass = classMapper.selectById(student.getClassid());
			FlowAll all = new FlowAll();
			all.setApplyid(flowHoliday.getId());
			all.setFlowmodeltypeid(flowHoliday.getFlowmodeltypeid());
			all.setStudid(flowHoliday.getStudid());
			all.setStatus(0);
			all.setRemark(studentClass.getClassname()+"班的"+student.getStuname()+"同学于"
					+flowHoliday.getStrattime()+"以"+flowHoliday.getRemark()+"理由申请请假");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/addFlowHolidayStud")
	@SystemControllerLog(description = "提交请假单")
	public @ResponseBody int addFlowHolidayStud(FlowHoliday holiday){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if (userInfo == null) {
			return -1;
		}
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null != holiday){
			holiday.setStudid(student.getId());
			holiday.setUpset(1);
			int count = holidayService.insertSelective(holiday);
			addHolidayDetial(student.getClassid(),count,holiday.getFlowmodeltypeid());
			
			StudentClass studentClass = classMapper.selectById(student.getClassid());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(holiday.getFlowmodeltypeid());
			all.setStudid(holiday.getStudid());
			all.setStatus(0);
			all.setRemark(studentClass.getClassname()+"班的"+student.getStuname()+"同学于"
					+holiday.getStrattime()+"以"+holiday.getRemark()+"理由申请请假");
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/deleteHolidayById", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "删除请假草稿")
	public @ResponseBody int deleteHolidayById(FlowHoliday flowHoliday) throws Exception{
		if(null!=flowHoliday){
			int count = holidayService.deleteByPrimaryKey(flowHoliday.getId());
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/lookHolidayDetial")
	@SystemControllerLog(description = "查看审批详情")
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
	
	private void addHolidayDetial(int classid,int holidayid,int flowMOdelTypeid){
		StudentClass studentClass = studentClassMapper.selectByPrimaryKey(classid);//获取写申请学生的班级信息
		String teacherId = studentClass.getTeacherId();//班主任id
		String clteacherId = studentClass.getClteacherId();//任课老师id
		
		FlowsModel flowsModel = new FlowsModel();
		flowsModel.setFlowmodeltypeid(flowMOdelTypeid);
		List<FlowsModel> flowsModels = modelService.selectSelective(flowsModel);//获取模板审批节点中的数据
		
		//新增申请单的审批节点
		for (int i = 0; i < flowsModels.size(); i++) {
			FlowHolidaydetail holidaydetail = new FlowHolidaydetail();
			holidaydetail.setHolidayid(holidayid);
			holidaydetail.setFlowmodelid(flowsModels.get(i).getId());
			holidaydetail.setStatus(0);//不出理
			if(flowsModels.get(i).getRoleid().equals("kong")){
				holidaydetail.setEmpid("");
				holidaydetail.setStatus(1);//通过
			}else if(flowsModels.get(i).getRoleid().equals("rkls")){
				holidaydetail.setEmpid(teacherId);
			}else if(flowsModels.get(i).getRoleid().equals("bzr")){
				holidaydetail.setEmpid(clteacherId);
			}else {
				holidaydetail.setEmpid(flowsModels.get(i).getEmpid());
			}
			holidaydetailService.insertSelective(holidaydetail);
		}
		
		updateHolidaydetial(holidayid);
	}
	
	//修改审批节点状态
	private void updateHolidaydetial(int holidayid){
		//修改
		List<FlowHolidaydetail> holidaydetails = holidaydetailService.selectAll(holidayid);
		if(holidaydetails.size()>0){
			FlowHolidaydetail holidaydetail = holidaydetails.get(0);
			holidaydetail.setStatus(2);//待审批
			holidaydetailService.updateByPrimaryKeySelective(holidaydetail);
		}else{
			FlowHoliday flowHoliday = new FlowHoliday();
			flowHoliday.setId(holidayid);
			flowHoliday.setUpset(3);//审批通过
			holidayService.updateByPrimaryKeySelective(flowHoliday);
			
			flowHoliday = holidayService.selectByPrimaryKey(holidayid);
			
			//修改总览表中对应的数据状态为已通过
			FlowAll all = new FlowAll();
			all.setApplyid(holidayid);
			all.setFlowmodeltypeid(flowHoliday.getFlowmodeltypeid());
			all.setStatus(1);
			allMapper.updateFlowAllStatus(all);
		}
	}
	
	@RequestMapping("/flowHolidayYes")
	@SystemControllerLog(description = "同意学生请假")
	public @ResponseBody int flowHolidayYes(FlowHolidaydetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(1);//审批通过
			int count = holidaydetailService.updateByPrimaryKeySelective(holidaydetail);
			updateHolidaydetial(holidaydetail.getHolidayid());
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping("/flowHolidayNo")
	@SystemControllerLog(description = "不同意学生请假")
	public @ResponseBody int flowHolidayNo(FlowHolidaydetail holidaydetail) throws Exception{ 
		if(null!=holidaydetail){
			holidaydetail.setStatus(3);//不同意申请
			int count = holidaydetailService.updateByPrimaryKeySelective(holidaydetail);
			
			//修改申请单的状态
			FlowHoliday flowHoliday = new FlowHoliday();
			flowHoliday.setId(holidaydetail.getHolidayid());
			flowHoliday.setUpset(2);//审批失败,申请单无效
			holidayService.updateByPrimaryKeySelective(flowHoliday);
			
			flowHoliday = holidayService.selectByPrimaryKey(holidaydetail.getHolidayid());
			
			//修改总览表中对应的数据状态为未通过
			FlowAll all = new FlowAll();
			all.setApplyid(holidaydetail.getHolidayid());
			all.setFlowmodeltypeid(flowHoliday.getFlowmodeltypeid());
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
}
