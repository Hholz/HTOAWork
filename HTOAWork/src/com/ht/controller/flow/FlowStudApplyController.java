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
import com.ht.mapper.flow.FlowAllMapper;
import com.ht.mapper.flow.FlowsModelMapper;
import com.ht.mapper.student.StudentClassMapper;
import com.ht.mapper.student.StudentFallMapper;
import com.ht.mapper.student.StudentMapper;
import com.ht.popj.flow.FlowAll;
import com.ht.popj.flow.FlowStudentApply;
import com.ht.popj.flow.FlowStudentApplyDetail;
import com.ht.popj.flow.FlowsModel;
import com.ht.popj.flow.FlowsModeltype;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.student.Student;
import com.ht.popj.student.StudentClass;
import com.ht.popj.student.StudentFall;
import com.ht.service.flow.FlowModeltypeService;
import com.ht.service.flow.FlowStudentApplyDetailService;
import com.ht.service.flow.FlowStudentApplyService;
import com.ht.util.ResultMessage;

@Controller
@RequestMapping("/flow/flowStudApply")
public class FlowStudApplyController {
	
	@Autowired
	FlowStudentApplyService applyService;
	@Autowired
	FlowModeltypeService modelTypeService;
	@Autowired
	StudentClassMapper classMapper;
	@Autowired
	StudentFallMapper fallMapper;
	@Autowired
	FlowAllMapper allMapper;
	@Autowired
	FlowsModelMapper modelMapper;
	@Autowired
	FlowStudentApplyDetailService applyDetailService;
	@Autowired
	StudentMapper studentMapper;

	@RequestMapping("/flowStudApply")
	@SystemControllerLog(description = "进入学生调班申请页面")
	public String flowStudApply(Model model) throws Exception{
		List<FlowsModeltype> modeltypes = modelTypeService.selectModelTypeByModelSelId("xstb");
		model.addAttribute("modeltypes", modeltypes);
		List<StudentFall> falls = fallMapper.selectFallAllList();
		model.addAttribute("falls", falls);
		return "flow/flowStudApply";
	}
	
	@RequestMapping("/findClassListByFallid")
	@SystemControllerLog(description = "返回所选界别的所有班级json数据")
	public @ResponseBody ResultMessage findClassListByFallid(Model model, StudentFall fall) {
		ResultMessage rm = new ResultMessage();
		List<StudentClass> classes = new ArrayList<>();
		if (fall.getId() == null) {
			rm.setTotal(1);
			rm.setRows(classes);
			return rm;
		}
		int fallid = fall.getId();
		classes = classMapper.selectClassListByFallid(fallid);
		rm.setTotal(classes.size());
		rm.setRows(classes);
		return rm;
	}
	
	@RequestMapping("/saveFlowStudentApply")
	@SystemControllerLog(description = "保存调班申请单为草稿模式")
	public @ResponseBody int saveFlowStudentApply(FlowStudentApply studentApply){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null != studentApply){
			studentApply.setStudid(student.getId());
			studentApply.setClassid1(student.getClassid());
			studentApply.setUpset(0);
			int count = applyService.insertSelective(studentApply);
			return count;
		}
		return 0;
	}
	
	//修改申请单状态
	@RequestMapping(value = "/updateStudentApplyUpset", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "提交未提交的申请单")
	public @ResponseBody int updateStudentApplyUpset(FlowStudentApply studentApply) throws Exception{
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null!=studentApply){
			studentApply.setUpset(1);
			int count = applyService.updateByPrimaryKeySelective(studentApply);//从未提交状态改为已提交状态
			
			studentApply = applyService.selectByPrimaryKey(studentApply.getId());//获取当前申请单的所有信息
			
			addStudentApplyHetial(studentApply.getClassid1(),studentApply.getClassid2(),studentApply.getId(),studentApply.getFlowmodeltypeid());//调用新增审批节点的方法
			
			StudentClass studentClass = classMapper.selectById(student.getClassid());
			StudentClass studentClass2 = classMapper.selectById(studentApply.getClassid2());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(studentApply.getFlowmodeltypeid());
			all.setStudid(studentApply.getStudid());
			all.setStatus(0);
			all.setRemark(studentClass.getClassname()+"班的"+student.getStuname()+"同学"
					+"申请调换到"+studentClass2.getClassname()+"理由为"+studentApply.getRemark());
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping(value = "/deleteStudentApplyById", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "删除请假草稿")
	public @ResponseBody int deleteStudentApplyById(FlowStudentApply studentApply) throws Exception{
		if(null!=studentApply){
			int count = applyService.deleteByPrimaryKey(studentApply.getId());
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/addFlowStudentApply")
	@SystemControllerLog(description = "提交调班申请单")
	public @ResponseBody int addFlowStudentApply(FlowStudentApply studentApply){
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		//2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Student student = null;
		if(null != userInfo.getStudent()){
			student = userInfo.getStudent();
		}
		if(null != studentApply){
			studentApply.setStudid(student.getId());
			studentApply.setClassid1(student.getClassid());
			studentApply.setUpset(1);
			int count = applyService.insertSelective(studentApply);
			addStudentApplyHetial(studentApply.getClassid1(),studentApply.getClassid2(),count,studentApply.getFlowmodeltypeid());
			
			StudentClass studentClass = classMapper.selectById(student.getClassid());
			StudentClass studentClass2 = classMapper.selectById(studentApply.getClassid2());
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(studentApply.getFlowmodeltypeid());
			all.setStudid(studentApply.getStudid());
			all.setStatus(0);
			all.setRemark(studentClass.getClassname()+"班的"+student.getStuname()+"同学"
					+"申请调换到"+studentClass2.getClassname()+"理由为"+studentApply.getRemark());
			allMapper.insertSelective(all);
			return count;
		}
		return 0;
	}
	
	@RequestMapping("/lookStudentApplyDetial")
	@SystemControllerLog(description = "查看审批详情")
	public @ResponseBody ResultMessage lookStudentApplyDetial(FlowStudentApply studentApply){
		ResultMessage rm = new ResultMessage();
		List<FlowStudentApplyDetail> list = new ArrayList<>();
		if (null != studentApply) {
			list = applyDetailService.selectByPrimaryKey(studentApply.getId());
		}
		rm.setRows(list);
		rm.setTotal(list.size());
		return rm;
	}
	
	private void addStudentApplyHetial(int classid1,int classid2,int count,int flowModelTypeid){
		StudentClass studentClass = classMapper.selectByPrimaryKey(classid1);//获取写申请学生的班级信息
		StudentClass studentClass2 = classMapper.selectByPrimaryKey(classid2);//获取写申请学生的班级信息
		FlowsModel flowsModel = new FlowsModel();
		flowsModel.setFlowmodeltypeid(flowModelTypeid);
		List<FlowsModel> flowsModels = modelMapper.selectSelective(flowsModel);//获取模板审批节点中的数据
		int a=0;
		int b=0;
		//新增申请单的审批节点
		for (int i = 0; i < flowsModels.size(); i++) {
			FlowStudentApplyDetail applyDetail = new FlowStudentApplyDetail();
			applyDetail.setStudapplyid(count);
			applyDetail.setFlowmodelid(flowsModels.get(i).getId());
			applyDetail.setStatus(0);//不出理
			if(flowsModels.get(i).getRoleid().equals("kong")){
				applyDetail.setEmpid("");
				applyDetail.setStatus(1);//通过
			}else if(flowsModels.get(i).getRoleid().equals("rkls")){
				if(a>0){
					applyDetail.setEmpid(studentClass2.getTeacherId());
				}else{
					applyDetail.setEmpid(studentClass.getTeacherId());
					a++;
				}
			}else if(flowsModels.get(i).getRoleid().equals("bzr")){
				if(b>0){
					applyDetail.setEmpid(studentClass2.getClteacherId());
				}else{
					applyDetail.setEmpid(studentClass.getClteacherId());
					b++;
				}
			}else {
				applyDetail.setEmpid(flowsModels.get(i).getEmpid());
			}
			applyDetailService.insertSelective(applyDetail);
		}
		
		updateApplyDetial(count);
	}
	
	//修改审批节点状态
	private void updateApplyDetial(int count){
		//修改
		List<FlowStudentApplyDetail> applyDetails = applyDetailService.selectAll(count);
		if(applyDetails.size()>0){
			FlowStudentApplyDetail applyDetail = applyDetails.get(0);
			applyDetail.setStatus(2);//待审批
			applyDetailService.updateByPrimaryKeySelective(applyDetail);
		}else{
			FlowStudentApply flowStudentApply = new FlowStudentApply();
			flowStudentApply.setId(count);
			flowStudentApply.setUpset(3);//审批通过
			applyService.updateByPrimaryKeySelective(flowStudentApply);
			
			flowStudentApply = applyService.selectByPrimaryKey(count);
			
			//修改总览表中对应的数据状态为已通过
			FlowAll all = new FlowAll();
			all.setApplyid(count);
			all.setFlowmodeltypeid(flowStudentApply.getFlowmodeltypeid());
			all.setStatus(1);
			allMapper.updateFlowAllStatus(all);
			
			//修改学生信息
			Student record = new Student();
			record.setId(flowStudentApply.getStudid());
			record.setClassid(flowStudentApply.getClassid2());
			studentMapper.updateByPrimaryKeySelective(record);
		}
	}
	
	@RequestMapping("/studentApplyYes")
	@SystemControllerLog(description = "同意学生请假")
	public @ResponseBody int studentApplyYes(FlowStudentApplyDetail studentApplyDetail) throws Exception{ 
		if(null!=studentApplyDetail){
			studentApplyDetail.setStatus(1);//审批通过
			int count = applyDetailService.updateByPrimaryKeySelective(studentApplyDetail);
			updateApplyDetial(studentApplyDetail.getStudapplyid());
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
	
	@RequestMapping("/flowStudentApplyNo")
	@SystemControllerLog(description = "不同意学生请假")
	public @ResponseBody int flowStudentApplyNo(FlowStudentApplyDetail studentApplyDetail) throws Exception{ 
		if(null!=studentApplyDetail){
			studentApplyDetail.setStatus(3);//不同意申请
			int count = applyDetailService.updateByPrimaryKeySelective(studentApplyDetail);
			
			//修改申请单的状态
			FlowStudentApply studentApply = new FlowStudentApply();
			studentApply.setId(studentApplyDetail.getStudapplyid());
			studentApply.setUpset(2);//审批失败,申请单无效
			applyService.updateByPrimaryKeySelective(studentApply);
			
			studentApply = applyService.selectByPrimaryKey(studentApplyDetail.getStudapplyid());
			
			//修改总览表中对应的数据状态为未通过
			FlowAll all = new FlowAll();
			all.setApplyid(studentApply.getId());
			all.setFlowmodeltypeid(studentApply.getFlowmodeltypeid());
			all.setStatus(2);
			allMapper.updateFlowAllStatus(all);
			return count;
		}
		//当student为空时，运行到这里，返回0
		return 0;
	}
}
