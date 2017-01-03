package com.ht.controller.dailyWork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
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
import com.ht.mapper.dailyWork.DepMapper;
import com.ht.mapper.dailyWork.EmpMapper;
import com.ht.popj.dailyWork.ApplyMaterial;
import com.ht.popj.dailyWork.Dep;
import com.ht.popj.dailyWork.Emp;
import com.ht.popj.dailyWork.Holiday;
import com.ht.popj.dailyWork.HolidayType;
import com.ht.popj.dailyWork.MaterialType;
import com.ht.popj.login.ShiroUserInfo;
import com.ht.popj.sysSet.FlowModel;
import com.ht.popj.sysSet.Flowschedule;
import com.ht.service.dailyWork.EmpService;
import com.ht.service.dailyWork.HolidayService;
import com.ht.service.dailyWork.HolidaytypeService;
import com.ht.service.sysSet.FlowModelService;
import com.ht.service.sysSet.FlowscheduleService;
import com.ht.util.RandomGet;
import com.ht.util.ResultMessage;

/*
 * 操作请假申请
 */
@Controller
@RequestMapping("/dailyWork/Holiday")
public class HolidayController {

	@Autowired
	HolidayService hservice;
	@Autowired
	HolidaytypeService htypeservice;
	@Autowired
	EmpService empservice;
	@Autowired
	FlowModelService fmservice;
	@Autowired
	FlowscheduleService fscheduleservice;
	@Autowired
	DepMapper depmapper;
	@Autowired
	EmpMapper empmapper;

	// 进入页面前查询 请假的类别
	@RequestMapping("/ApplyHolidayList/{id}")
	@SystemControllerLog(description = "进入请假/加班/调休申请页面")
	public String List(Model model, @PathVariable("id") Integer id) {

		HolidayType ht = new HolidayType();
		
		List<HolidayType> materialtypelist = new ArrayList<HolidayType>();
		
		List<Dep> deplist = depmapper.selectDepList();

		model.addAttribute("deplist", deplist);
		
		String retu = "";
		
		if (id == 1) {
			ht.setHolidaytypename("假");
			materialtypelist = htypeservice.selectByName(ht);
			model.addAttribute("mlist", materialtypelist);
			retu = "/dailyWork/ApplyHolidayList";
		} else if (id == 2) {
			ht.setHolidaytypename("加班");
			materialtypelist = htypeservice.selectByName(ht);
			model.addAttribute("mlist", materialtypelist);
			retu = "/dailyWork/OverWorkList";
		} else if (id == 3) {
			ht.setHolidaytypename("调休");
			materialtypelist = htypeservice.selectByName(ht);
			model.addAttribute("mlist", materialtypelist);
			retu = "/dailyWork/ChangeHolidayList";
		}
		return retu;
	}
	
	//返回所选部门的所有员工json数据
	@RequestMapping("/findemp")
	@SystemControllerLog(description = "返回所选部门的所有员工json数据")
	public @ResponseBody ResultMessage findemp(Model model, Dep dep) {
		Emp e = new Emp();
		ResultMessage rm = new ResultMessage();
		e.setDepid(dep.getId());
		List<Emp> emplist = empmapper.selectEmp(e);
		rm.setTotal(emplist.size());
		rm.setRows(emplist);
		return rm;
	}

	//新增员工请假/加班/调休申请
	@RequestMapping("/add")
	@SystemControllerLog(description = "新增员工请假/加班/调休申请")
	public @ResponseBody int add(Model model, Holiday holiday) {
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		// 2.从session中取得ShiroUserInfo对象
		ShiroUserInfo userInfo = (ShiroUserInfo) session.getAttribute("userInfo");
		Emp emp = null;
		// 3.从userInfo中取出学生或员工信息//两者有一为空，注意判空
		if (null != userInfo.getEmp()) {
			emp = userInfo.getEmp();
		}
		FlowModel f = new FlowModel();
		List<FlowModel> flist = fmservice.findList(f);
		int typeid = holiday.getHolidaytypeid();
		Date beginDate = null;
		Date endDate = null;
		Long day= null;
		int d =0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		try {
			beginDate = sdf.parse(holiday.getStarttime());
			endDate = sdf.parse(holiday.getEndtime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		if(typeid == 1 || typeid == 2 || typeid ==5){//私假,公假,病假
			for (int i = 0; i < flist.size(); i++) {
				f = flist.get(i);
				if (f.getFlowmodelname().equals("员工填写请假单")) {
					// 获取"填写申购单"的流程id
					holiday.setFlowmodelid(f.getId());
					break;
				}
			}
			day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
			d = day.intValue();
			
			
		}else if(typeid == 3){//加班
			for (int i = 0; i < flist.size(); i++) {
				f = flist.get(i);
				if (f.getFlowmodelname().equals("员工填写加班申请单")) {
					// 获取"填写申购单"的流程id
					holiday.setFlowmodelid(f.getId());
					break;
				}
			}
			day = (endDate.getTime() - beginDate.getTime()) / (60 * 60 * 1000);
			d = day.intValue();
			
			
		}else if(typeid == 4){//调休
			for (int i = 0; i < flist.size(); i++) {
				f = flist.get(i);
				if (f.getFlowmodelname().equals("员工填写调休申请单")) {
					// 获取"填写申购单"的流程id
					holiday.setFlowmodelid(f.getId());
					break;
				}
			}
			d = 0;
		}
		
		
		holiday.setHolidayday(d);
		holiday.setEmpid(emp.getId());
		return hservice.insertSelective(holiday);
	}

	//返回员工请假/加班/调休的json数据
	@RequestMapping(value = "/{id}", method = { RequestMethod.POST })
	@SystemControllerLog(description = "返回员工请假/加班/调休的json数据")
	public @ResponseBody ResultMessage findList(Integer limit, Integer offset, @PathVariable("id") Integer id,
			Holiday applymaterial) {
		ResultMessage rm = new ResultMessage();
		int pageCount = (int) Math.ceil(offset / (limit * 1.0));
		PageHelper.startPage(pageCount + 1, limit);
		int type = 0;
		List<Holiday> materiallist = new ArrayList<Holiday>();
		if(null != applymaterial.getHolidaytypeid()){
			type =applymaterial.getHolidaytypeid();
		}
		 
		if(id == 1){//请假类别1.2.3
			applymaterial.setId(null);
			applymaterial.setHolidaytypeid(1);
			materiallist.addAll(hservice.selectByName(applymaterial));
			
			applymaterial.setHolidaytypeid(2);
			materiallist.addAll(hservice.selectByName(applymaterial));
		
			applymaterial.setHolidaytypeid(5);
			materiallist.addAll(hservice.selectByName(applymaterial));
			if(type != 0){
				materiallist.clear();
				applymaterial.setHolidaytypeid(type);
				materiallist.addAll(hservice.selectByName(applymaterial));
			}
			
		}else if(id == 3){//加班类别
			applymaterial.setId(null);
			applymaterial.setHolidaytypeid(3);
			materiallist.addAll(hservice.selectByName(applymaterial));
		}else if(id == 4){//调休类别
			applymaterial.setId(null);
			applymaterial.setHolidaytypeid(4);
			materiallist.addAll(hservice.selectByName(applymaterial));
		}
		
		

		PageInfo<Holiday> pageInfo = new PageInfo<Holiday>(materiallist);
		long total = pageInfo.getTotal();
		System.out.println("-----" + total);
		rm.setTotal((int) total);
		rm.setRows(materiallist);
		return rm;
	}

	// 修改员工请假/加班/调休信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.PUT })
	@SystemControllerLog(description = "修改员工请假/加班/调休信息")
	public @ResponseBody int updata(Model model, Holiday applymaterial) {
		hservice.updateByPrimaryKeySelective(applymaterial);
		return 1;
	}

	// 删除员工请假/加班/调休信息
	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	@SystemControllerLog(description = "删除员工请假/加班/调休信息")
	public @ResponseBody int delete(Model model, @PathVariable("id") Integer id) {
		hservice.deleteByPrimaryKey(id);
		return 1;
	}

	// 提交员工请假/加班/调休信息
	@RequestMapping(value = "/upset")
	@SystemControllerLog(description = "提交员工请假/加班/调休信息")
	public @ResponseBody int upset(Model model, Holiday applymaterial) {
		// 设置已提交的归还单的审批状态为1
		applymaterial.setApprovalstatus(1);
		// 1为已提交
		applymaterial.setUpset(1);

		// 生成单号
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String str = sdf.format(d);
		System.out.println(str);
		str = str.substring(0, 8);
		
		FlowModel f = new FlowModel();
		applymaterial = hservice.selectByPrimaryKey(applymaterial.getId());
		
		int typeid = applymaterial.getHolidaytypeid();
		if(typeid == 1 || typeid == 2 || typeid ==5){//私假,公假,病假
			str = str + RandomGet.getSix() + "YGQJD";
			f.setFlowmodeltypeid(7);
		}else if(typeid == 3){//加班类别
			str = str + RandomGet.getSix() + "YGJBD";
			f.setFlowmodeltypeid(10);
		}else if(typeid == 4){//调休类别
			str = str + RandomGet.getSix() + "YGTXD";
			f.setFlowmodeltypeid(11);
		}
		
		// 设置单号
		applymaterial.setAppnum(str);
		applymaterial.setApprovalstatus(1);
		// 1为已提交
		applymaterial.setUpset(1);
		
		// 流程进度
		Flowschedule fsch = new Flowschedule();
		
		// 查询办公用品申购类别的所有流程
		List<FlowModel> flist = fmservice.findList(f);
		for (int i = 0; i < flist.size(); i++) {
			f = flist.get(i);
			fsch.setApplynum(str);
			if (i == 0) {
				// 第一个流程为填单,已完成
				fsch.setFlowstatus(1);
			} else {
				// 后续流程未完成
				fsch.setFlowstatus(0);
			}
			// 新增时,id为空
			fsch.setId(null);

			fsch.setFlowtype(f.getFlowmodeltypeid());
			fsch.setFlowdot(f.getId());
			fscheduleservice.insertSelective(fsch);
		}

		hservice.updateByPrimaryKeySelective(applymaterial);
		return 1;
	}

}
