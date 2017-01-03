package com.ht.controller.dailyWork;


import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ht.annotation.SystemControllerLog;
import com.ht.popj.dailyWork.Baoxiao;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.ApprovalTitle;
import com.ht.service.dailyWork.BaoxiaoService;
import com.ht.service.sysSet.ApprovalTitleService;

@Controller
@RequestMapping("/dailyWork/BaoxiaoTask")
public class BaoxiaoTaskController {
	
	@Autowired
	BaoxiaoService baoxiaoservice;
	
	@Autowired
	ApprovalTitleService atitleservice;
	
	private List<Baoxiao> tasklist=new ArrayList<Baoxiao>();
	public List<Baoxiao> overlist=new ArrayList<Baoxiao>();
	Emp emp = new Emp();
	
	@RequestMapping("/baoxiaotasklist")
	@SystemControllerLog(description = "进入报销审批页面")
	public String seletedepList(Model model){
		emp = new Emp();
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		if(null!=userInfo.getEmp()){
			emp = userInfo.getEmp();
			tasklist=baoxiaoservice.selectBaoxiaoTask(emp);
			overlist=baoxiaoservice.selectBaoxiaoOvertask(emp);
			model.addAttribute("overtasklist", overlist);
			model.addAttribute("tasklist", tasklist);
			model.addAttribute("user", emp);
		}
		return "dailyWork/BaoxiaoTask";
	}
	
	@RequestMapping(value = "/over/{yn}")
	@SystemControllerLog(description = "报销审批")
	public  @ResponseBody int updata(Model model, Baoxiao baoxiao,@PathVariable("yn") int yn) {
		baoxiao=baoxiaoservice.selectByPrimaryKey(baoxiao.getId());
		int i=0;
		if(yn==1){//审批通过
			if(baoxiao.getFlowid()==16){//部门领导审批通过
				i=16;
				baoxiao.setFlowid(17);
				baoxiao.setFlowstatus(2);
			}else{//财务部审批通过
				i=17;
				baoxiao.setFlowstatus(3);
			}
			setapprovaltitle(baoxiao,1,i);
			baoxiaoservice.updateByPrimaryKey(baoxiao);
		}else{//审批不通过
			if(baoxiao.getFlowid()==16){//部门领导审批不通过
				i=16;
				baoxiao.setFlowid(17);
				baoxiao.setFlowstatus(-2);
			}else{//财务部审批不通过
				i=17;
				baoxiao.setFlowstatus(-3);
			}
			setapprovaltitle(baoxiao,0,i);
			baoxiaoservice.updateByPrimaryKey(baoxiao);
		}
		return 1;
	}
	private void setapprovaltitle(Baoxiao baoxiao,int yn,int flowid){
		ApprovalTitle title = new ApprovalTitle();
		title.setEmpid(emp.getId());
		if(yn==1){
			title.setTitlestutas("C");
		}else{
			title.setTitlestutas("D");
		}
		title.setAppman(baoxiao.getEmpid());
		title.setCreateman(baoxiao.getEmpid());
		title.setApprovalnum(baoxiao.getApplynum());
		title.setFlowid(flowid);
		title.setApprovalman(emp.getId());
		title.setDepid(emp.getDepid());
		atitleservice.insertSelective(title);
	}
}
